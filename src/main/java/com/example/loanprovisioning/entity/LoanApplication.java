package com.example.loanprovisioning.entity;


import com.example.loanprovisioning.dto.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "LOAN_APPLICATION")

public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_APPLICATION_ID")
    private Long loanApplicationId;
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @Column(name = "LOAN_AMOUNT_REQUESTED")
    private double loanAmountRequested;
    @Column(name = "TERM")
    private int term;
    @Column(name = "PURPOSE")
    private String purpose;
    @Column(name = "BANK_ACCOUNT_NUMBER")
    private String bankAccountNumber;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    @Column(name = "APPLICATION_DATE")
    @CreationTimestamp
    private Timestamp applicationDate;
    @Column(name = "APPROVAL_DATE")
    private Timestamp approvalDate;
    @Column(name = "DISBURSEMENT_DATE")
    private Timestamp disbursementDate;
    @UpdateTimestamp
    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;
    @Column(name = "OUTSTANDING_BALANCE")
    private double outstandingBalance;
    @Column(name = "TOTAL_INTEREST_PAID")
    private double totalInterestPaid;
}
