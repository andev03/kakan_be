package com.kakan.payment_service.controller;

import com.kakan.payment_service.dto.response.PaymentResponse;
import com.kakan.payment_service.dto.response.ResponseDto;
import com.kakan.payment_service.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @GetMapping("/vnpay-return")
    public ResponseDto<PaymentResponse> handleVNPayReturn(HttpServletRequest request) {
        try {
            PaymentResponse result = paymentService.handleVNPayReturn(request);
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
