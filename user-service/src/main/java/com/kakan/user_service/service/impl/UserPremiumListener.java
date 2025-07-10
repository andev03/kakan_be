package com.kakan.user_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakan.user_service.dto.PaymentEvent;
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

    @KafkaListener(topics = "new-payments", groupId = "payments-group")
    public void onPaymentSucceeded(String paymentEvent) throws JsonMappingException, JsonProcessingException {
        PaymentEvent p = new ObjectMapper().readValue(paymentEvent, PaymentEvent.class);
        try {
            accountRepository.findById(p.getOrder().getAccountId()).ifPresent(account -> {
                account.setRole("PREMIUM"); // hoặc cập nhật role khác nếu cần
                accountRepository.save(account);
            });
        } catch (Exception e) {
            throw e;
        }
    }
}
