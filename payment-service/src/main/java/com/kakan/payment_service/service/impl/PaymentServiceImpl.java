package com.kakan.payment_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakan.payment_service.config.VNPayConfig;
import com.kakan.payment_service.dto.*;
import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.dto.response.PaymentResponse;
import com.kakan.payment_service.enums.PaymentEnums;
import com.kakan.payment_service.pojo.Payment;
import com.kakan.payment_service.repository.PaymentRepository;
import com.kakan.payment_service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.kakan.payment_service.config.VNPayConfig.secretKey;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private Integer paymentId;

    private OrderCreatedEvent orderCreatedEvent;

    @Autowired
    private  KafkaTemplate<String, OrderCreatedEvent> kafkaOrderTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @KafkaListener(topics = "new-orders", groupId = "orders-group")
    public void processPayment(String event) throws  JsonProcessingException {
        LOGGER.info(String.format("message received -> %s", event ));
        System.out.println("Recieved event for payment " + event);
        OrderCreatedEvent orderEvent = new ObjectMapper().readValue(event, OrderCreatedEvent.class);
        orderCreatedEvent = orderEvent;
        CustomerOrder order = orderEvent.getOrder();

        Payment payment = new Payment();

        try {
            payment.setOrderId(order.getOrderId());
            payment.setAccountId(order.getAccountId());
            payment.setAmount(order.getAmount());
            payment.setPaymentMethod("VNPAY");
            payment.setPaymentDate(OffsetDateTime.now());
            payment.setStatus(PaymentEnums.PENDING.name());
            payment.setResponseMessage("Chờ thanh toán");
            Payment payment1 = paymentRepository.save(payment);

            paymentId = payment1.getPaymentId();
        } catch (Exception e) {
            payment.setOrderId(order.getOrderId());
            payment.setStatus("FAILED");
            paymentRepository.save(payment);
        }
    }

    @Override
    public CreatePaymentResponse createPaymentURL(CustomerOrder order, HttpServletRequest request) {
            Payment payment = paymentRepository.findById(paymentId).orElse(null);
            // Chuẩn bị tham số cho VNPAY
            // VNPAY yêu cầu số tiền là long (đã nhân 100).
            // Dùng BigDecimal để giữ độ chính xác cao hơn
            BigDecimal rawAmount = BigDecimal.valueOf(order.getAmount()).multiply(BigDecimal.valueOf(100));
            long amount = rawAmount.longValue(); // Đây là giá trị cần truyền cho VNPAY

            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_TxnRef = order.getOrderId() + "_" + payment.getPaymentId(); // OrderId_PaymentId
            String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
            String vnp_OrderType = "other"; // Required parameter
            String vnp_IpAddr = "127.0.0.1";

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", vnp_OrderType); // Added missing parameter
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_BankCode", "NCB");
            vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();

            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    // Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    try {
                        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    // Build query - DON'T encode field names, only values
                    query.append(fieldName);
                    query.append('=');
                    try {
                        query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnp_SecureHash = VNPayConfig.hmacSHA512(secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

//        CreatePaymentResponse paymentResponse = new CreatePaymentResponse();
//        paymentResponse.setMessage("Tạo URL thanh toán thành công");
//        paymentResponse.setPaymentUrl(paymentUrl);
            return CreatePaymentResponse.builder()
                    .message("Tạo URL thanh toán thành công")
                    .paymentUrl(paymentUrl)
                    .build();
    }

    @Override
    public PaymentResponse vnPayReturn(HttpServletRequest request) {



        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = (String) fields.remove("vnp_SecureHash");
        if (secretKey != null && VNPayConfig.hashAllFields(fields).equals(vnp_SecureHash)) {
            String vnp_ResponseCode = (String) fields.get("vnp_ResponseCode");
            String vnp_TransactionStatus = (String) fields.get("vnp_TransactionStatus");
            String vnp_TxnRef = (String) fields.get("vnp_TxnRef"); // OrderId_TransactionId
            String vnp_OrderInfo = (String) fields.get("vnp_OrderInfo");
            long amountFromVNPAY = Long.parseLong((String) fields.get("vnp_Amount"));
            Double amount = amountFromVNPAY / 100.0; // Parse ngược lại


            String[] txnRefParts = vnp_TxnRef.split("_");
            Integer orderId = Integer.parseInt(txnRefParts[0]);
            Integer paymentId = Integer.parseInt(txnRefParts[1]);

//            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);

            if (optionalPayment.isPresent()) {
//                Order order = optionalOrder.get();
                Payment payment = optionalPayment.get();

                // Cập nhật trạng thái giao dịch
//                transaction.setResponseMessage(vnp_OrderInfo);
//                transaction.setAmount(amount);

                // Kiểm tra mã phản hồi và trạng thái giao dịch VNPAY
                if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                    // Giao dịch thành công
//                    order.setStatus(OrderStatus.ACTIVE.name());
//                    order.setNote("Đã thanh toán, đơn hàng đã được kích hoạt");
//                    order.setExpiredDate(OffsetDateTime.now().plusDays(30)); // Đặt ngày hết hạn là 30 ngày kể từ khi thanh toán thành công
                    payment.setStatus(PaymentEnums.SUCCESS.name());
                    payment.setResponseMessage("Thanh toán thành công");

                    PaymentEvent paymentEvent = new PaymentEvent();
                    paymentEvent.setOrder(orderCreatedEvent.getOrder());
                    kafkaTemplate.send("new-payments", paymentEvent);
                    orderCreatedEvent = null;
                } else {
                    // Giao dịch thất bại
//                    order.setStatus(OrderStatus.CANCELLED.name());
//                    order.setNote("Thanh toán thất bại.");
                    payment.setStatus(PaymentEnums.FAILED.name());
                    payment.setResponseMessage("Thanh toán thất bại. Mã lỗi VNPAY: " + vnp_ResponseCode + ", Trạng thái: " + vnp_TransactionStatus);

                    OrderCreatedEvent oe = new OrderCreatedEvent();
                    oe.setOrder(orderCreatedEvent.getOrder());
                    kafkaOrderTemplate.send("reversed-orders", orderCreatedEvent);
                    orderCreatedEvent = null;
                }
//                orderRepository.save(order);
                paymentRepository.save(payment);
                return PaymentResponse.builder()
                        .code(vnp_ResponseCode)
                        .message(payment.getResponseMessage())
                        .build();

            } else {
                return PaymentResponse.builder()
                        .code("99") // Lỗi logic hoặc không tìm thấy đơn hàng/giao dịch
                        .message("Không tìm thấy đơn hàng hoặc giao dịch tương ứng.")
                        .build();
            }

        } else {
            return PaymentResponse.builder()
                    .code("97") // Chu ký không hợp lệ
                    .message("Chữ ký không hợp lệ")
                    .build();
        }
    }
}
