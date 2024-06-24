package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;

@Builder
public record ValidationErrorResponse(
        @JsonProperty("status") int status,
        @JsonProperty("message") String message,
        @JsonProperty("validationErrors") Map<String, String> validationErrors) {
}