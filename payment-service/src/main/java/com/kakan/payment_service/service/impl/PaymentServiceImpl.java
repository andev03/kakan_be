package com.kakan.payment_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakan.payment_service.config.VNPayConfig;
import com.kakan.payment_service.dto.OrderCreatedEvent;
import com.kakan.payment_service.dto.PaymentEvent;
import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.dto.response.PaymentDto;
import com.kakan.payment_service.enums.PaymentEnums;
import com.kakan.payment_service.mapper.PaymentMapper;
import com.kakan.payment_service.pojo.Payment;
import com.kakan.payment_service.repository.PaymentRepository;
import com.kakan.payment_service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.kakan.payment_service.config.VNPayConfig.secretKey;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final VNPayConfig vnPayConfig;

    private final PaymentRepository paymentRepository;

    private final ObjectMapper objectMapper;

    private final PaymentMapper paymentMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "order.success", groupId = "orders-kakan-group")
    public void handleOrderEvent(String orderString) throws JsonProcessingException {

        OrderCreatedEvent order = objectMapper.readValue(orderString, OrderCreatedEvent.class);
        Payment payment = new Payment();
        payment.setOrderId(order.getOrderId());
        payment.setAccountId(order.getAccountId());
        payment.setAmount(order.getAmount());
        payment.setPaymentMethod("VnPay");
        payment.setStatus(PaymentEnums.PENDING.name());
        payment.setResponseMessage("Chờ thanh toán");
        payment.setPaymentUrl(getPaymentUrl(order));
        paymentRepository.save(payment);
    }

    @Override
    public CreatePaymentResponse getPaymentUrl(Integer accountId, Integer orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);

        return CreatePaymentResponse.builder()
                .message("Lấy URL thanh toán thành công")
                .paymentUrl(
                        getPaymentUrl(new OrderCreatedEvent(
                                orderId,
                                accountId,
                                payment.getAmount(),
                                "PENDING"
                        )))
                .build();
    }

    private String getPaymentUrl(OrderCreatedEvent order) {
        BigDecimal rawAmount = BigDecimal.valueOf(order.getAmount()).multiply(BigDecimal.valueOf(100));
        long amount = rawAmount.longValue();

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = order.getOrderId() + "-" + UUID.randomUUID().toString().substring(0, 6);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String vnp_OrderType = "other";
        String vnp_IpAddr = "127.0.0.1";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());
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
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

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

        return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
    }

    @Override
    public PaymentDto handleVNPayReturn(HttpServletRequest request) throws JsonProcessingException {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }
        String vnp_TxnRef = (String) fields.get("vnp_TxnRef");

        String[] parts = vnp_TxnRef.split("-");
        int orderId = Integer.parseInt(parts[0]);

        System.out.println("Order ID: " + orderId);
        Payment payment = paymentRepository.findByOrderId(orderId);

        payment.setStatus(PaymentEnums.SUCCESS.name());
        payment.setPaymentUrl(null);
        payment.setResponseMessage("Thanh toán thành công");

        payment = paymentRepository.save(payment);

        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderId(payment.getOrderId());
        paymentEvent.setPaymentStatus(payment.getStatus());

        kafkaTemplate.send("payment.success", objectMapper.writeValueAsString(paymentEvent));

        return paymentMapper.toDto(payment);
    }
}
