package com.kakan.user_service.pojo;

import com.kakan.user_service.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "\"transaction\"")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "transaction_method", nullable = false, length = 100)
    private String transactionMethod;

    @Column(name = "transaction_date", nullable = false)
    private OffsetDateTime transactionDate;


    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "response_message", columnDefinition = "TEXT")
    private String responseMessage;

}