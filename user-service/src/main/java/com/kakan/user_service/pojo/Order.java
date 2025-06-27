package com.kakan.user_service.pojo;

import com.kakan.user_service.enums.OrderStatus;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "package_name", nullable = false, length = 255)
    private String packageName;

    @Column(name = "package_duration", nullable = false)
    private Integer packageDuration;

    @Column(nullable = false)
    private Double price;

    @Column(name = "order_date", nullable = false)
    private OffsetDateTime orderDate;

    @Column(name = "expired_date", nullable = false)
    private OffsetDateTime expiredDate;


    @Column(nullable = false, length = 10)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String note;
}