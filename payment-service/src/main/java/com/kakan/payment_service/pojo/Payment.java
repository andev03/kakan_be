package com.kakan.payment_service.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "\"payment\"")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "payment_method", nullable = false, length = 100)
    private String paymentMethod;

    @Column(name = "payment_date", nullable = false)
    private OffsetDateTime paymentDate = OffsetDateTime.now();

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "response_message", columnDefinition = "TEXT")
    private String responseMessage;

    @Column(name = "payment_url", columnDefinition = "TEXT")
    private String paymentUrl;
}
