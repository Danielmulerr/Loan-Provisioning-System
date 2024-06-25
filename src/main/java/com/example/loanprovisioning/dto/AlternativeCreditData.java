package com.example.loanprovisioning.dto;

public record AlternativeCreditData(
        int changeOfAddress,
        int changeOfAddressPeriod,
        int lateRentalPayments,
        int lateRentalPaymentsPeriod,
        int lateCellPhonePayments,
        int lateCellPhonePaymentsPeriod
) {
}