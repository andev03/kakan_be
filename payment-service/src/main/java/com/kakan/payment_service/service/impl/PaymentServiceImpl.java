package com.kakan.payment_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakan.payment_service.config.VNPayConfig;
import com.kakan.payment_service.dto.OrderCreatedEvent;
import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.dto.response.PaymentResponse;
import com.kakan.payment_service.enums.PaymentEnums;
import com.kakan.payment_service.pojo.Payment;
import com.kakan.payment_service.repository.PaymentRepository;
import com.kakan.payment_service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.kakan.payment_service.config.VNPayConfig.secretKey;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @KafkaListener(topics = "order.success", groupId = "orders-kakan-group")
    public void handleOrderEvent(String event) throws JsonProcessingException {
        OrderCreatedEvent order = new ObjectMapper().readValue(event, OrderCreatedEvent.class);

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
    public CreatePaymentResponse getPaymentUrl(CreatePaymentRequest createPaymentRequest) {
        return CreatePaymentResponse.builder()
                .message("Lấy URL thanh toán thành công")
                .paymentUrl(
                        getPaymentUrl(new OrderCreatedEvent(
                                createPaymentRequest.getOrderId(),
                                createPaymentRequest.getAccountId(),
                                createPaymentRequest.getAmount()
                        )))
                .build();
    }

    private String getPaymentUrl(OrderCreatedEvent order){
        BigDecimal rawAmount = BigDecimal.valueOf(order.getAmount()).multiply(BigDecimal.valueOf(100));
        long amount = rawAmount.longValue();

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = order.getOrderId() + "";
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
    public PaymentResponse handleVNPayReturn(HttpServletRequest request) {
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
            String vnp_TxnRef = (String) fields.get("vnp_TxnRef");
            String vnp_OrderInfo = (String) fields.get("vnp_OrderInfo");
            long amountFromVNPAY = Long.parseLong((String) fields.get("vnp_Amount"));
            Double amount = amountFromVNPAY / 100.0;

            Integer orderId = Integer.parseInt(vnp_TxnRef);

        }

        return null;
    }
}
