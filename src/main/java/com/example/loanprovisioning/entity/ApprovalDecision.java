package com.example.loanprovisioning.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "APPROVAL_DECISION")
public class ApprovalDecision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPROVAL_DECISION_ID")
    private Long approvalDecisionId;
    @OneToOne
    @JoinColumn(name = "LOAN_APPLICATION_ID")
    private LoanApplication loanApplication;
    @Column(name = "APPROVAL_DATE")
    private Timestamp approvalDate;
    @Column(name = "APPROVAL_STATUS")
    private String approvalStatus;
    @Column(name = "REASON")
    private String reason;

}
