package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.entity.CreditScore;
import com.example.loanprovisioning.entity.LoanApplication;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class LoanProcessingService {
    private final KieContainer kieContainer;

    public LoanProcessingService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public LoanApplication processLoanApplication(LoanApplication application, CreditScore creditScore) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(application);
        kieSession.insert(creditScore);
        kieSession.fireAllRules();
        return application;
    }
}
