package com.kakan.user_service.controller;

import com.kakan.user_service.dto.request.PaymentRequest;
import com.kakan.user_service.dto.response.CreatePaymentResponse;
import com.kakan.user_service.dto.response.PaymentResponse;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.service.impl.VNPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Validated
@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayController {
    @Autowired
    VNPayServiceImpl vnPayService;

    @PostMapping("/create-payment")
    public ResponseDto<CreatePaymentResponse> createPayment(@RequestBody @Valid PaymentRequest paymentRequest, HttpServletRequest request)  {
        try {
            CreatePaymentResponse result = vnPayService.createPaymentUrl(paymentRequest, request);
            return ResponseDto.<CreatePaymentResponse>builder()
                    .message("Payment URL created successfully")
                    .data(result)
                    .status(HttpStatus.OK.value())
                    .build();
        } catch (UnsupportedEncodingException e) {
            return ResponseDto.<CreatePaymentResponse>builder()
                    .message("Error creating payment URL: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    @GetMapping("/vnpay-return")
    public ResponseDto<PaymentResponse> vnpayReturn(HttpServletRequest request) {
        try {
            PaymentResponse result = vnPayService.vnpayReturn(request);
            return ResponseDto.<PaymentResponse>builder()
                    .message("Payment processed successfully")
                    .data(result)
                    .status(HttpStatus.OK.value())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<PaymentResponse>builder()
                    .message("Error processing payment: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }



}
