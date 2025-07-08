package com.kakan.user_service.service;

import com.kakan.user_service.dto.PaymentSucceededEvent;
import com.kakan.user_service.repository.AccountRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserPremiumListener {
    private final AccountRepository accountRepository;

    public UserPremiumListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @KafkaListener(
            topics = "payment.succeeded",
            containerFactory = "paymentSucceededKafkaListenerFactory"
    )
    public void onPaymentSucceeded(PaymentSucceededEvent evt) {
        accountRepository.findById(evt.getUserId()).ifPresent(account -> {
            account.setRole("PREMIUM"); // hoặc cập nhật role khác nếu cần
            accountRepository.save(account);
        });
    }
}
