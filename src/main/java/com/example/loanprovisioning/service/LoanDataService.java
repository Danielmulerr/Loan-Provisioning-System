package com.example.loanprovisioning.service;

import com.example.loanprovisioning.dto.AlternativeCreditData;
import com.example.loanprovisioning.dto.MockRequestDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface LoanDataService {
    @Async
    CompletableFuture<Integer> fetchCreditScore(MockRequestDto mockRequestDto);

    @Async
    CompletableFuture<Double> calculateDtiRatio(MockRequestDto mockRequestDto);

    @Async
    CompletableFuture<AlternativeCreditData> fetchAlternativeCreditData(MockRequestDto mockRequestDto);
}
