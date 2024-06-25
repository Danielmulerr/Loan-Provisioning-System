package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record MockRequestDto(
        @JsonProperty("userId")
        Long userId,
        @JsonProperty("email")
        String email) implements Serializable {
}