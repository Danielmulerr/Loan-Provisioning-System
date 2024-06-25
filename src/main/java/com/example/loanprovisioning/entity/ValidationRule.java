package com.example.loanprovisioning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//@Entity
//@Setter
//@Getter
//@Table(name = "VALIDATION_RULE",
//        indexes = {
//                @Index(name = "v_max_score_index", columnList = "MAX_DTI_RATIO"),
//                @Index(name = "v_min_score_index", columnList = "MIN_CREDIT_SCORE"),
//                @Index(name = "v_dti_ratio_index", columnList = "MAX_DTI_RATIO"),
//        },
//        uniqueConstraints =
//                {@UniqueConstraint(name = "UniqueWallet",
//                        columnNames = {"MIN_CREDIT_SCORE", "MAX_DTI_RATIO", "ADDITIONAL_VALIDATIONS_REQUIRED", "APPROVAL_STRATEGY"})})
//public class ValidationRule {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "VALIDATION_RULE_ID")
//    private Long validationRuleId;
//    @Column(name = "MIN_CREDIT_SCORE")
//    private long minCreditScore;
//    @Column(name = "MAX_DTI_RATIO")
//    private double maxDTIRatio;
//    @Column(name = "ADDITIONAL_VALIDATIONS_REQUIRED")
//    private boolean additionalValidationsRequired;
//    @Column(name = "APPROVAL_STRATEGY")
//    private String approvalStrategy;
//
//}
