package com.example.loanprovisioning.entity;

import com.example.loanprovisioning.dto.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

//@Entity
//@Table(name = "LOANS")
@Setter
@Getter
public class Loan {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "LOAN_ID")
//    private Long loanId;
//    @ManyToOne
//    @JoinColumn(name = "USER_ID", nullable = false)
//    private User user;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "TERM")
    private int term;
    @Column(name = "PURPOSE")
    private String purpose;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private LoanStatus status;
    @CreationTimestamp
    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;
    @Column(name = "APPROVAL_DATE")
    private Timestamp approvalDate;
    @Column(name = "DISBURSEMENT_DATE")
    private Timestamp disbursementDate;
}