package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

import static com.example.loanprovisioning.config.AppConstants.NAME_PATTERN;
import static com.example.loanprovisioning.config.AppConstants.PHONE_PATTERN;

public record LoanApplicationRequestDto(
        @JsonProperty("firstName")
        @NotEmpty(message = "First name field can not be empty.")
        @Pattern(regexp = NAME_PATTERN) String firstName,
        @JsonProperty("lastName")
        @NotEmpty(message = "Last name field can not be empty.")
        @Pattern(regexp = NAME_PATTERN) String lastName,
        @JsonProperty("email")
        @NotEmpty(message = "Email field can not be empty.")
        @Email String email,
        @JsonProperty("phone")
        @NotEmpty(message = "Phone field can not be empty.")
        @Pattern(regexp = PHONE_PATTERN) String phone,
        @JsonProperty("loanAmount")
        @NotNull Double loanAmount, @JsonProperty("loanTerm")
        @NotNull Integer loanTerm,
        @JsonProperty("loanPurpose") String loanPurpose) implements Serializable {
}