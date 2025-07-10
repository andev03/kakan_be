package com.kakan.payment_service.service;

import com.kakan.payment_service.dto.CustomerOrder;
import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {

    CreatePaymentResponse createPaymentURL(CustomerOrder order, HttpServletRequest request);
    PaymentResponse vnPayReturn(HttpServletRequest request);
}
