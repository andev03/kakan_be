package com.kakan.payment_service.controller;

import com.kakan.payment_service.dto.request.CreatePaymentRequest;
import com.kakan.payment_service.dto.response.CreatePaymentResponse;
import com.kakan.payment_service.dto.response.PaymentDto;
import com.kakan.payment_service.dto.response.ResponseDto;
import com.kakan.payment_service.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @GetMapping("/vnpay-return")
    public ResponseDto<PaymentDto> handleVNPayReturn(HttpServletRequest request) {
        try {
            PaymentDto result = paymentService.handleVNPayReturn(request);
            return ResponseDto.<PaymentDto>builder()
                    .message("Payment processed successfully")
                    .data(result)
                    .status(HttpStatus.OK.value())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<PaymentDto>builder()
                    .message("Error processing payment: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    @GetMapping("/vnpay/url/{accountId}/{orderId}")
    public ResponseDto<Object> getPaymentUrl(@PathVariable Integer accountId, @PathVariable Integer orderId) {

        CreatePaymentResponse paymentUrl = paymentService.getPaymentUrl(accountId, orderId);

        return ResponseDto.builder()
                .message("Payment processed successfully")
                .data(paymentUrl)
                .status(HttpStatus.OK.value())
                .build();
    }
}
