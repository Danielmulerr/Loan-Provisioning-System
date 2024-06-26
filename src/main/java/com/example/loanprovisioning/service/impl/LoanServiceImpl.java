package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.dto.*;
import com.example.loanprovisioning.entity.CreditScore;
import com.example.loanprovisioning.repository.CreditScoreRepository;
import com.example.loanprovisioning.entity.LoanApplication;
import com.example.loanprovisioning.entity.User;
import com.example.loanprovisioning.exception.CustomeException;
import com.example.loanprovisioning.exception.GeneralException;
import com.example.loanprovisioning.exception.ResourceNotFoundException;
import com.example.loanprovisioning.repository.LoanApplicationRepository;
import com.example.loanprovisioning.repository.UserRepository;
import com.example.loanprovisioning.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;
import static com.example.loanprovisioning.utils.TimeUtils.getFromDate;
import static com.example.loanprovisioning.utils.TimeUtils.getToDate;
import static com.example.loanprovisioning.utils.Utils.prepareResponse;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final CreditScoreRepository creditScoreRepository;
    private final ModelMapper modelMapper;
    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final MockLoanDataServiceImpl mockLoanDataServiceImpl;
    @Value("${origination-fee-rate}")
    private double originationFeeRate;

    public LoanServiceImpl(UserRepository userRepository,
                           LoanApplicationRepository loanApplicationRepository,
                           ModelMapper modelMapper, MockLoanDataServiceImpl mockLoanDataServiceImpl,
                           CreditScoreRepository creditScoreRepository) {
        this.userRepository = userRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.modelMapper = modelMapper;
        this.mockLoanDataServiceImpl = mockLoanDataServiceImpl;
        this.creditScoreRepository = creditScoreRepository;
    }

    @Override
    public ResponseEntity<GenericResponse> getAllLoans(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        try {
            val loanApplications = loanApplicationRepository.findByApplicationDateBetween(getFromDate(toDate), getToDate(toDate), pageable)
                    .map((element) -> modelMapper.map(element, LoanApplicationDto.class));
            return prepareResponse(HttpStatus.OK, "Successfully submitted Loan",
                    loanApplications);
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while submitting loan. ", "Try again later", e);
            throw new GeneralException("Failed while submitting loan. Try again later");
        }
    }

    @Override
    public ResponseEntity<GenericResponse> getLoanById(Long loanId) {
        try {
            val loanApplication = loanApplicationRepository.findById(loanId)
                    .map((element) -> modelMapper.map(element, LoanApplicationDto.class))
                    .orElseThrow(() -> new ResourceNotFoundException("Unable to find loan with id:" + loanId));
            return prepareResponse(HttpStatus.OK, "Successfully fetched Loan",
                    loanApplication
            );
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while fetching loan. ", "Try again later", e);
            throw new GeneralException("Failed while fetching loan. Try again later");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<GenericResponse> createLoanApplication(LoanApplicationRequestDto loanApplicationRequestDto) {
        try {
            val currentUser = userRepository.findByEmailIgnoreCase(loanApplicationRequestDto.email())
                    .orElse(prepareNeUseInfo(loanApplicationRequestDto));
            var updatedUser = userRepository.save(currentUser);
            var loanApplication = getLoanApplication(loanApplicationRequestDto, updatedUser);
            val saveLoanApplication = loanApplicationRepository.save(loanApplication);
            fetchAndStoreCreditInfo(updatedUser);
            return prepareResponse(HttpStatus.CREATED, "Successfully create Loan Application",
                    modelMapper.map(saveLoanApplication, LoanApplicationDto.class));
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while create Loan Application. ", "Try again later", e);
            throw new GeneralException("Failed while create Loan Application. Try again later");
        }
    }

    private LoanApplication getLoanApplication(LoanApplicationRequestDto loanApplicationRequestDto, User updatedUser) {
        var loanApplication = new LoanApplication();
        loanApplication.setUser(updatedUser);
        loanApplication.setPurpose(loanApplicationRequestDto.loanPurpose());
        loanApplication.setLoanAmountRequested(loanApplicationRequestDto.loanAmount());
        loanApplication.setStatus(LoanStatus.PENDING);
        loanApplication.setTerm(loanApplicationRequestDto.loanTerm());
        loanApplication.setOutstandingBalance(calculateInitialOutstandingBalance(loanApplication.getLoanAmountRequested()));
        return loanApplication;
    }

    private double calculateInitialOutstandingBalance(double loanAmountRequested) {
        return loanAmountRequested + (loanAmountRequested * originationFeeRate);
    }

    private CreditScore fetchAndStoreCreditInfo(User user) {
        try {
            val request = new MockRequestDto(user.getUserId(), user.getEmail());
            CompletableFuture<Integer> creditScore = mockLoanDataServiceImpl.fetchCreditScore(request);
            CompletableFuture<Double> dtiRatio = mockLoanDataServiceImpl.calculateDtiRatio(request);
            CompletableFuture<AlternativeCreditData> alternativeCreditData = mockLoanDataServiceImpl.fetchAlternativeCreditData(request);
            var creditScoreInfo = new CreditScore();
            creditScoreInfo.setUser(user);
            CompletableFuture.allOf(creditScore, dtiRatio, alternativeCreditData);

            creditScoreInfo.setCreditScore(creditScore.get());
            creditScoreInfo.setDtiRatio(dtiRatio.get());
            val alternative = alternativeCreditData.get();
            creditScoreInfo.setChangeOfAddress(alternative.changeOfAddress());
            creditScoreInfo.setChangeOfAddressPeriod(alternative.changeOfAddressPeriod());
            creditScoreInfo.setLateRentalPayments(alternative.lateRentalPayments());
            creditScoreInfo.setLateRentalPaymentsPeriod(alternative.lateRentalPaymentsPeriod());
            creditScoreInfo.setLateCellPhonePayments(alternative.lateCellPhonePayments());
            creditScoreInfo.setLateCellPhonePaymentsPeriod(alternative.lateRentalPaymentsPeriod());
            return creditScoreRepository.save(creditScoreInfo);
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while create preparing credit related info. ", "Try again later", e);
            throw new GeneralException("Failed while create preparing credit related info. Try again later");
        }
    }

    private User prepareNeUseInfo(LoanApplicationRequestDto loanApplicationRequestDto) {
        var user = new User();
        user.setPhone(loanApplicationRequestDto.phone());
        user.setEmail(loanApplicationRequestDto.email());
        user.setFirstName(loanApplicationRequestDto.firstName());
        user.setLastName(loanApplicationRequestDto.lastName());
        return user;
    }
}
