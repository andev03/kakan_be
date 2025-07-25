package com.kakan.order_service.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "order_date", nullable = false)
    private OffsetDateTime orderDate = OffsetDateTime.now();

    @Column(name = "expired_date", nullable = false)
    private OffsetDateTime expiredDate ;
}
