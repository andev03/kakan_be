package com.kakan.user_service.service.impl;


import com.kakan.user_service.config.VNPayConfig;
import com.kakan.user_service.dto.request.PaymentRequest;
import com.kakan.user_service.dto.response.CreatePaymentResponse;
import com.kakan.user_service.dto.response.PaymentResponse;
import com.kakan.user_service.enums.OrderStatus;
import com.kakan.user_service.enums.TransactionStatus;
import com.kakan.user_service.pojo.Order;
import com.kakan.user_service.pojo.Transaction;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.repository.OrderRepository;
import com.kakan.user_service.repository.TransactionRepository;
import com.kakan.user_service.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;

import static com.kakan.user_service.config.VNPayConfig.secretKey;

@Service
public class VNPayServiceImpl implements VNPayService {

    @Autowired
    private VNPayConfig vnPayConfig;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;



    public CreatePaymentResponse createPaymentUrl(PaymentRequest paymentRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + paymentRequest.getOrderId()));
        if( order.getStatus().equals(OrderStatus.ACTIVE.name())) {
            throw new IllegalArgumentException("Order is already active and cannot be paid again.");
        }
        if (order.getStatus().equals(OrderStatus.EXPIRED.name())) {
            throw new IllegalArgumentException("Order is expired and cannot be paid.");
        }
        if (order.getStatus().equals(OrderStatus.CANCELLED.name())) {
            throw new IllegalArgumentException("Order is cancelled and cannot be paid.");
        }
        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setAmount(50000.00);
        transaction.setTransactionMethod("VNPay");
        transaction.setTransactionDate(OffsetDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING.name());
        transaction.setResponseMessage("Chờ thanh toán");
        transaction = transactionRepository.save(transaction);

        // Chuẩn bị tham số cho VNPAY
        // VNPAY yêu cầu số tiền là long (đã nhân 100).
        // Dùng BigDecimal để giữ độ chính xác cao hơn
        BigDecimal rawAmount = BigDecimal.valueOf(order.getPrice()).multiply(BigDecimal.valueOf(100));
        long amount = rawAmount.longValue(); // Đây là giá trị cần truyền cho VNPAY

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = order.getOrderId() + "_" + transaction.getTransactionId(); // OrderId_TransactionId
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
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                // Build query - DON'T encode field names, only values
                query.append(fieldName);
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

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
        CreatePaymentResponse paymentResponse = new CreatePaymentResponse();
        paymentResponse.setMessage("Tạo URL thanh toán thành công");
        paymentResponse.setPaymentUrl(paymentUrl);
        return paymentResponse;
    }



    public PaymentResponse vnpayReturn(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
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
            Integer transactionId = Integer.parseInt(txnRefParts[1]);

            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

            if (optionalOrder.isPresent() && optionalTransaction.isPresent()) {
                Order order = optionalOrder.get();
                Transaction transaction = optionalTransaction.get();

                // Cập nhật trạng thái giao dịch
//                transaction.setResponseMessage(vnp_OrderInfo);
//                transaction.setAmount(amount);

                // Kiểm tra mã phản hồi và trạng thái giao dịch VNPAY
                if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                    // Giao dịch thành công
                    order.setStatus(OrderStatus.ACTIVE.name());
                    order.setNote("Đã thanh toán, đơn hàng đã được kích hoạt");
//                    order.setExpiredDate(OffsetDateTime.now().plusDays(30)); // Đặt ngày hết hạn là 30 ngày kể từ khi thanh toán thành công
                    transaction.setStatus(TransactionStatus.SUCCESS.name());
                    transaction.setResponseMessage("Thanh toán thành công");
                } else {
                    // Giao dịch thất bại
                    order.setStatus(OrderStatus.CANCELLED.name());
                    order.setNote("Thanh toán thất bại.");
                    transaction.setStatus(TransactionStatus.FAILED.name());
                    transaction.setResponseMessage("Thanh toán thất bại. Mã lỗi VNPAY: " + vnp_ResponseCode + ", Trạng thái: " + vnp_TransactionStatus);
                }
                orderRepository.save(order);
                transactionRepository.save(transaction);

                return PaymentResponse.builder()
                        .code(vnp_ResponseCode)
                        .message(transaction.getResponseMessage())
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
