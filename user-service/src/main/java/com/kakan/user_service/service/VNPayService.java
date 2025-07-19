package com.kakan.user_service.service;

import com.kakan.user_service.dto.request.PaymentRequest;
import com.kakan.user_service.dto.response.CreatePaymentResponse;
import com.kakan.user_service.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface VNPayService {




        CreatePaymentResponse createPaymentUrl(PaymentRequest paymentRequest, HttpServletRequest request) throws UnsupportedEncodingException;

        PaymentResponse vnpayReturn(HttpServletRequest request) ;


}
