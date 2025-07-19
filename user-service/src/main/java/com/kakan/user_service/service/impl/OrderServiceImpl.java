package com.kakan.user_service.service.impl;

import com.kakan.user_service.dto.request.OrderStatusRequest;
import com.kakan.user_service.enums.AccountRole;
import com.kakan.user_service.exception.EntityNotFoundException;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AccountRepository accountRepository;


    public void updateRole(OrderStatusRequest orderStatusRequest) {
        int updated = accountRepository.updateRoleById(orderStatusRequest.getAccountId(), AccountRole.PREMIUM.name());
        if (updated == 0) {
            throw new EntityNotFoundException("Account not found with ID: " + orderStatusRequest.getAccountId());
        }
    }
}
