package com.kakan.user_service.service.impl;

import com.kakan.user_service.dto.request.OrderStatusRequest;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AccountRepository accountRepository;

    public void updateRole(OrderStatusRequest orderStatusRequest) {
        // Logic to update the role based on the order status
        // This method will be called when an order status is updated
        // For example, if the order status is "COMPLETED", update the user's role to "PREMIUM"
        Account account = accountRepository.findAccountById(orderStatusRequest.getAccountId());
        if(orderStatusRequest.getStatus().equals("SUCCESS")) {
            account.setRole("PREMIUM");
            accountRepository.save(account);// or any other role based on your logic
        }
    }
}
