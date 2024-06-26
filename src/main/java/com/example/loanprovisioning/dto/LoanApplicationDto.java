package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class LoanApplicationDto {
    @JsonProperty("loanApplicationId")
    private Long loanApplicationId;
    @JsonProperty("applicationDate")
    private Timestamp applicationDate;
    @JsonProperty("loanAmountRequested")
    private double loanAmountRequested;
    @JsonProperty("term")
    private int term;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("status")
    private LoanStatus status;
    private Timestamp approvalDate;
    @JsonProperty("disbursementDate")
    private Timestamp disbursementDate;
    @JsonProperty("lastUpdateDate")
    private Timestamp lastUpdateDate;
    @JsonProperty("outstandingBalance")
    private double outstandingBalance;
    @JsonProperty("totalInterestPaid")
    private double totalInterestPaid;
}
