package com.example.loanprovisioning.config.properties;

public record AutoStructuringRules(
        int downPaymentIncrement,
        int monthlyPaymentReductionPercent,
        int dtiCap,
        int loanTermIncrementMonths,
        int maxLoanTermMonths,
        int monthlyPaymentReductionAmount,
        double interestRateIncreasePercent
) {
}