package com.example.loanprovisioning.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "CREDIT_SCORE")
public class CreditScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CREDIT_SCORE_ID")
    private Long creditScoreId;
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @Column(name = "CREDIT_SCORE")
    private Integer creditScore;
    @Column(name = "DTI_RATIO")
    private Double dtiRatio;
    @Column(name = "MAX_DTI_RATIO")
    private Double maxDTIRatio;
    @Column(name = "CHANGE_OF_ADDRESS")
    private Integer changeOfAddress;
    @Column(name = "CHANGE_OF_ADDRESS_PERIOD")
    private Integer changeOfAddressPeriod;
    @Column(name = "LATE_RENTAL_PAYMENTS")
    private Integer lateRentalPayments;
    @Column(name = "LATE_RENTAL_PAYMENTS_PERIOD")
    private Integer lateRentalPaymentsPeriod;
    @Column(name = "LATE_CELL_PHONE_PAYMENTS")
    private Integer lateCellPhonePayments;
    @Column(name = "LATE_CELL_PHONE_PAYMENTS_PERIOD")
    private Integer lateCellPhonePaymentsPeriod;
    @Column(name = "SCORE_DATE")
    private Timestamp scoreDate;
    @Column(name = "CREDIT_BUREAU_SOURCE")
    private String creditBureauSource;

}
