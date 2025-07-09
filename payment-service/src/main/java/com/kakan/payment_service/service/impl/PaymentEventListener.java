package com.kakan.payment_service.service.impl;

import com.kakan.payment_service.dto.OrderCreatedEvent;
import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentEventListener {
    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventListener(PaymentService paymentService,
                                KafkaTemplate<String, Object> kafkaTemplate) {
        this.paymentService = paymentService;
        this.kafkaTemplate = kafkaTemplate;
        log.info("PaymentEventListener initialized successfully");
    }

    @KafkaListener(
            topics = "order.created",
            containerFactory = "orderCreatedKafkaListenerFactory"
    )
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("PaymentEventListener received OrderCreatedEvent: orderId={}, accountId={}, amount={}",
                event.getOrderId(), event.getAccountID(), event.getAmount());
        
        try {
            // 1) Build request để tạo URL VNPAY
            CreatePaymentRequest req = CreatePaymentRequest.builder()
                    .orderId(event.getOrderId())
                    .accountId(event.getAccountID())
                    .amount(event.getAmount())
                    .build();

            // 2) Gọi service để lưu payment và trả về URL
            //    Vì listener không có HttpServletRequest, ta có thể truyền null,
            //    hoặc nếu bạn cần lấy host/port thì extract trước rồi truyền vào.
            CreatePaymentResponse resp = paymentService.createPaymentURL(req, null);
            log.info("Successfully created payment URL for orderId: {}", event.getOrderId());

            // 3) (Tùy chọn) nếu bạn muốn đẩy URL này lên một topic để front-end hoặc service khác
            //    sử dụng, bạn có thể publish tiếp:
            kafkaTemplate.send(
                    new ProducerRecord<>("payment.url.created",
                            event.getOrderId().toString(),
                            resp.getPaymentUrl())
            );
            log.info("Successfully sent payment URL to payment.url.created topic for orderId: {}", event.getOrderId());
            
            // Nếu bạn không dùng topic này thì có thể log hoặc lưu vào DB,
            // front-end sẽ gọi trực tiếp API thanh toán để lấy URL.
        } catch (Exception e) {
            log.error("Error processing OrderCreatedEvent for orderId {}: {}", event.getOrderId(), e.getMessage(), e);
            throw e; // Re-throw để Kafka có thể retry nếu cần
        }
    }
}
