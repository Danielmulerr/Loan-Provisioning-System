package com.example.loanprovisioning.config.properties;

public record AlternativeCreditDataRules(
        int changeOfAddressThreshold,
        int changeOfAddressPeriod,
        int lateRentalPaymentsThreshold,
        int lateRentalPaymentsPeriod,
        int lateCellPhonePaymentsThreshold,
        int lateCellPhonePaymentsPeriod
) {
}

