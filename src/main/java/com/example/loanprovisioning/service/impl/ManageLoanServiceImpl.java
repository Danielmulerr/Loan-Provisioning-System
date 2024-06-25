package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.entity.CreditScoreRepository;
import com.example.loanprovisioning.exception.CustomeException;
import com.example.loanprovisioning.exception.GeneralException;
import com.example.loanprovisioning.exception.ResourceNotFoundException;
import com.example.loanprovisioning.repository.LoanApplicationRepository;
import com.example.loanprovisioning.service.MangeLoanService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;
import static com.example.loanprovisioning.utils.Utils.prepareResponse;
import static java.util.Collections.emptyList;

@Service
@Slf4j
public class ManageLoanServiceImpl implements MangeLoanService {
    private final CreditScoreRepository creditScoreRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanProcessingService loanProcessingService;

    public ManageLoanServiceImpl(LoanProcessingService loanProcessingService,
                                 LoanApplicationRepository loanApplicationRepository,
                                 CreditScoreRepository creditScoreRepository) {
        this.loanProcessingService = loanProcessingService;
        this.loanApplicationRepository = loanApplicationRepository;
        this.creditScoreRepository = creditScoreRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<GenericResponse> approveLoan(Long loanId) {
        try {
            val loanApplication = loanApplicationRepository.findById(loanId)
                    .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id: " + loanId));
            val creditScore = creditScoreRepository.findByUser_UserId(loanApplication.getUser().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Credit Score not found with user id: " + loanApplication.getUser().getUserId()));
            log.info("Before processing application = {}", loanApplication);
            val application = loanProcessingService.processLoanApplication(loanApplication, creditScore);
            log.info("After processing application = {}", application);
            return prepareResponse(HttpStatus.OK, "Successfully approved Loan", emptyList());
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while approving loan. ", "Try again later", e);
            throw new GeneralException("Failed while approving loan. Try again later");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<GenericResponse> rejectLoan(Long loanId) {
        try {
            return prepareResponse(HttpStatus.OK, "Successfully rejected Loan", emptyList());
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while rejecting loan. ", "Try again later", e);
            throw new GeneralException("Failed while rejecting loan. Try again later");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<GenericResponse> disburseLoan(Long loanId) {
        try {
            return prepareResponse(HttpStatus.OK, "Successfully disbursing Loan", emptyList());
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while disbursing loan. ", "Try again later", e);
            throw new GeneralException("Failed while disbursing loan. Try again later");
        }
    }

}
