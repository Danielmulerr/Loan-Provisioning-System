package com.example.loanprovisioning.config.properties;

public record RuleConfig(
        long minCreditScore,
        long maxDTIRatio,
        boolean additionalValidationsRequired,
        String approvalStrategy
) {
}