package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record MockRequestDto(
        @JsonProperty("subject")
        String subject,
        @JsonProperty("amount")
        Long amount,
        @JsonProperty("returnUrl")
        String returnUrl,
        @JsonProperty("callbackUrl")
        String callbackUrl,
        @JsonProperty("expiresIn")
        long expiresIn,
        @JsonProperty("paymentIntentUniqueId") String uniqueId) implements Serializable {
}