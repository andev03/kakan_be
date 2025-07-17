package com.kakan.payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.dto.response.PaymentDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {

    CreatePaymentResponse getPaymentUrl(Integer accountId, Integer orderId);
    PaymentDto handleVNPayReturn(HttpServletRequest request) throws JsonProcessingException;
}
