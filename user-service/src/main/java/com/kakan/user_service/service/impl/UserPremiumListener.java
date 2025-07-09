package com.kakan.user_service.service.impl;

import com.kakan.user_service.dto.PaymentSucceededEvent;
import com.kakan.user_service.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPremiumListener {
    private final AccountRepository accountRepository;

    public UserPremiumListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        log.info("UserPremiumListener initialized successfully");
    }

    @KafkaListener(
            topics = "payment.succeeded",
            containerFactory = "paymentSucceededKafkaListenerFactory"
    )
    public void onPaymentSucceeded(PaymentSucceededEvent evt) {
        log.info("UserPremiumListener received PaymentSucceededEvent: orderId={}, accountId={}, paymentId={}",
                evt.getOrderId(), evt.getAccountID(), evt.getPaymentId());
        
        try {
            accountRepository.findById(evt.getAccountID()).ifPresent(account -> {
                log.info("Updating account {} from role {} to PREMIUM", account.getId(), account.getRole());
                account.setRole("PREMIUM"); // hoặc cập nhật role khác nếu cần
                accountRepository.save(account);
                log.info("Successfully updated account {} to PREMIUM role", account.getId());
            });
        } catch (Exception e) {
            log.error("Error processing PaymentSucceededEvent for accountId {}: {}", evt.getAccountID(), e.getMessage(), e);
            throw e; // Re-throw để Kafka có thể retry nếu cần
        }
    }
}
