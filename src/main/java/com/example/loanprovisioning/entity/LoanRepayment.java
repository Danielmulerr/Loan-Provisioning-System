package com.example.loanprovisioning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "LOAN_REPAYMENT")
public class LoanRepayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPAYMENT_ID")
    private Long repaymentId;

    @ManyToOne
    @JoinColumn(name = "LOAN_APPLICATION_ID", nullable = false)
    private LoanApplication loanApplication;

    @Column(name = "REPAYMENT_AMOUNT", nullable = false)
    private double repaymentAmount;

    @Column(name = "REPAYMENT_DATE", nullable = false)
    @CreationTimestamp
    private Timestamp repaymentDate;
}