package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
public class LoanRepaymentDto {
    @JsonProperty("repaymentId")
    private Long repaymentId;

    @JsonProperty("repaymentAmount")
    private double repaymentAmount;

    @JsonProperty("repaymentDate")
    private Timestamp repaymentDate;
}