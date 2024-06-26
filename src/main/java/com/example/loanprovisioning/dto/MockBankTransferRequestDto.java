package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record MockBankTransferRequestDto(
        @JsonProperty("debitAccount")
        String debitAccount,
        @JsonProperty("creditAccount")
        String creditAccount,
        @JsonProperty("amount")
        BigDecimal amount,
        @JsonProperty("otp")
        String otp
) implements Serializable {
}