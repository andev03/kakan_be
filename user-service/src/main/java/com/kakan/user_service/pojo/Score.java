package com.kakan.user_service.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "score", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "subject_id"})
})
@Getter
@Setter
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Integer scoreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "score_year_10")
    private Double scoreYear10;

    @Column(name = "score_year_11")
    private Double scoreYear11;

    @Column(name = "score_year_12")
    private Double scoreYear12;

    @Column(name = "gpa")
    private Double gpa;

}
