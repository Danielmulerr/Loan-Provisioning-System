package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.dto.LoanRepaymentDto;
import com.example.loanprovisioning.entity.LoanApplication;
import com.example.loanprovisioning.entity.LoanRepayment;
import com.example.loanprovisioning.repository.LoanRepaymentRepository;
import com.example.loanprovisioning.exception.CustomeException;
import com.example.loanprovisioning.exception.GeneralException;
import com.example.loanprovisioning.exception.ResourceNotFoundException;
import com.example.loanprovisioning.repository.LoanApplicationRepository;
import com.example.loanprovisioning.service.LoanRepayService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;
import static com.example.loanprovisioning.utils.Utils.prepareResponse;

@Service
@Slf4j
public class LoanRepayServiceImpl implements LoanRepayService {
    private final ModelMapper modelMapper;
    private final LoanRepaymentRepository loanRepaymentRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    @Value("${interest-rate}")
    private double interestRate;

    public LoanRepayServiceImpl(LoanApplicationRepository loanApplicationRepository,
                                LoanRepaymentRepository loanRepaymentRepository,
                                ModelMapper modelMapper) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanRepaymentRepository = loanRepaymentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<GenericResponse> repayLoan(Long loanId, double repaymentAmount) {
        try {
            var loanApplication = loanApplicationRepository.findById(loanId)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid loan application ID"));

            double monthlyInterestRate = calculateMonthlyInterestRate(loanApplication);
            double interest = loanApplication.getOutstandingBalance() * monthlyInterestRate;
            double principal = repaymentAmount - interest;

            loanApplication.setOutstandingBalance(loanApplication.getOutstandingBalance() - principal);
            loanApplication.setTotalInterestPaid(loanApplication.getTotalInterestPaid() + interest);

            var repayment = new LoanRepayment();
            repayment.setLoanApplication(loanApplication);
            repayment.setRepaymentAmount(repaymentAmount);
            val repaymentRecord = loanRepaymentRepository.save(repayment);
            loanApplicationRepository.save(loanApplication);
            return prepareResponse(HttpStatus.OK, "Successfully repay Loan",
                    modelMapper.map(repaymentRecord, LoanRepaymentDto.class));
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while repaying loan. ", "Try again later", e);
            throw new GeneralException("Failed while repaying loan. Try again later");
        }
    }

    @Override
    public ResponseEntity<GenericResponse> repaymentHistory(Long loanId) {
        try {
            val loanRepayment = loanRepaymentRepository.findByLoanApplication_LoanApplicationId(loanId);
            return prepareResponse(HttpStatus.OK, "Fetched repayment history",
                    loanRepayment
                            .stream()
                            .map((element) -> modelMapper.map(element, LoanRepaymentDto.class)).collect(Collectors.toList()));
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while fetching loan repayment History. ", "Try again later", e);
            throw new GeneralException("Failed while fetching loan repayment History. Try again later");
        }
    }

    @Override
    public ResponseEntity<GenericResponse> repaymentHistory() {
        try {
            val loanRepayment = loanRepaymentRepository.findAll();
            return prepareResponse(HttpStatus.OK, "All repayment history",
                    loanRepayment.stream().map((element) -> modelMapper.map(element, LoanRepaymentDto.class)).collect(Collectors.toList()));
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while fetching loan repayment History. ", "Try again later", e);
            throw new GeneralException("Failed while fetching loan repayment History. Try again later");
        }
    }

    private double calculateMonthlyInterestRate(LoanApplication loanApplication) {
        return interestRate / 12;
    }
}
