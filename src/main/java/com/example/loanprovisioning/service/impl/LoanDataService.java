package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.config.feign.FeignServiceClient;
import com.example.loanprovisioning.dto.AlternativeCreditData;
import com.example.loanprovisioning.dto.MockRequestDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class LoanDataService {
    private final FeignServiceClient feignServiceClient;

    public LoanDataService(FeignServiceClient feignServiceClient) {
        this.feignServiceClient = feignServiceClient;
    }

    @Async
    public CompletableFuture<Integer> fetchCreditScore(MockRequestDto mockRequestDto) {
        //val creditScoreResponse = feignServiceClient.fetchCreditScore(mockRequestDto);

        // Mock logic to fetch credit score from bureau
        return CompletableFuture.completedFuture(getMockCreditScore()); // Mocked value
    }


    @Async
    public CompletableFuture<Double> calculateDtiRatio(MockRequestDto mockRequestDto) {
        //val salaryInfo = feignServiceClient.fetchSalaryInfo(mockRequestDto);

        // Mock logic to calculate DTI ratio
        return CompletableFuture.completedFuture(getMockDtiRation());
    }


    @Async
    public CompletableFuture<AlternativeCreditData> fetchAlternativeCreditData(MockRequestDto mockRequestDto) {
        //val alternativeCredit = feignServiceClient.fetchAlternativeCreditData(mockRequestDto);

        // Mocked values
        return CompletableFuture.completedFuture(
                getMockAlternativeCredit());
    }

    private static double getMockDtiRation() {
        return 0.95;
    }

    private static int getMockCreditScore() {
        return 500;
    }

    private static AlternativeCreditData getMockAlternativeCredit() {
        return new AlternativeCreditData(
                2, 1, 0, // Mocked values for changeOfAddressCount, lateRentalPaymentsCount, lateCellPhonePaymentsCount
                1, 24, 36 // Mocked values for lateUtilityPaymentsCount, stableAddressPeriod (months), employmentStability (months)
        );
    }
}
