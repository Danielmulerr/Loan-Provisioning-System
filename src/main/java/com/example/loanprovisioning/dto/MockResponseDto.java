package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record MockResponseDto(
        @JsonProperty("paymentUrl")
        String paymentUrl,
        @JsonProperty("transactionId")
        String transactionId,
        @NotNull
        @JsonProperty("status")
        String status,
        @JsonProperty("message")
        String message
) implements Serializable {
}
