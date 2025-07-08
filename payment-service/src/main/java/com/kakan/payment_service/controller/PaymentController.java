package com.kakan.payment_service.controller;

import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.dto.response.PaymentResponse;
import com.kakan.payment_service.dto.response.ResponseDto;
import com.kakan.payment_service.service.PaymentService;
import com.kakan.payment_service.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;


    @PostMapping("/create-payment")
    public ResponseDto<CreatePaymentResponse> createPayment(@RequestBody @Valid CreatePaymentRequest createPaymentRequest, HttpServletRequest request)  {

            CreatePaymentResponse result = paymentService.createPaymentURL(createPaymentRequest, request);
            return ResponseDto.<CreatePaymentResponse>builder()
                    .message("Payment URL created successfully")
                    .data(result)
                    .status(HttpStatus.OK.value())
                    .build();

    }

    @GetMapping("/vnpay-return")
    public ResponseDto<PaymentResponse> vnpayReturn(HttpServletRequest request) {
        try {
            PaymentResponse result = paymentService.vnPayReturn(request);
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
