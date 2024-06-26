package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record PspPaymentResponseDto(
        @JsonProperty("transactionId")
        String transactionId,
        @NotNull
        @JsonProperty("status")
        PaymentStatus status,
        @JsonProperty("message")
        String message
) implements Serializable {
}


