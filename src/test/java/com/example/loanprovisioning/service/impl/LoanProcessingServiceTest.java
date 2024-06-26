package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.config.feign.BankFeignClient;
import com.example.loanprovisioning.dto.LoanStatus;
import com.example.loanprovisioning.entity.CreditScore;
import com.example.loanprovisioning.entity.LoanApplication;
import com.example.loanprovisioning.repository.LoanRepaymentRepository;
import com.example.loanprovisioning.entity.User;
import com.example.loanprovisioning.repository.LoanApplicationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanProcessingServiceTest {

    @Mock
    private KieSession kieSession;
    @Mock
    private BankFeignClient bankFeignClient;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private LoanRepaymentRepository loanRepaymentRepository;
    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private LoanProcessingService loanProcessingService;

    private static User testUser;
    private static CreditScore creditScore;
    private static LoanApplication loanApplication;

    @BeforeAll
    public static void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPhone("1234567890");
    }

    @BeforeEach
    public void init() {
        // Reset mocks before each test
        reset(kieSession);
    }

    @Test
    void testAutomaticApproval() {
        // Credit score >= 700 and DTI ratio <= 0.4
        creditScore = new CreditScore();
        creditScore.setUser(testUser);
        creditScore.setCreditScore(720);
        creditScore.setDtiRatio(0.3);

        loanApplication = new LoanApplication();
        loanApplication.setUser(testUser);
        loanApplication.setLoanAmountRequested(5000);
        loanApplication.setTerm(12);
        loanApplication.setPurpose("Education");
        loanApplication.setStatus(LoanStatus.PENDING);

        when(kieSession.insert(any())).thenReturn(mock(FactHandle.class));

        loanProcessingService.processLoanApplication(loanApplication, creditScore);

        verify(kieSession, times(2)).insert(any());
        verify(kieSession).fireAllRules();

        assertEquals(LoanStatus.APPROVED, loanApplication.getStatus());
    }

    @Test
    void testManualApproval() {
        // Credit score between 600 and 699, DTI ratio <= 0.5
        creditScore = new CreditScore();
        creditScore.setUser(testUser);
        creditScore.setCreditScore(650);
        creditScore.setDtiRatio(0.45);

        loanApplication = new LoanApplication();
        loanApplication.setUser(testUser);
        loanApplication.setLoanAmountRequested(5000);
        loanApplication.setTerm(12);
        loanApplication.setPurpose("Education");
        loanApplication.setStatus(LoanStatus.PENDING);

        when(kieSession.insert(any())).thenReturn(mock(FactHandle.class));

        loanProcessingService.processLoanApplication(loanApplication, creditScore);

        verify(kieSession, times(2)).insert(any());
        verify(kieSession).fireAllRules();

        assertEquals(LoanStatus.MANUAL_REVIEW, loanApplication.getStatus());
    }

    @Test
    void testRejectionBasedOnCreditScoreAndDTI() {
        // Credit score < 600, DTI ratio > 0.5
        creditScore = new CreditScore();
        creditScore.setUser(testUser);
        creditScore.setCreditScore(580);
        creditScore.setDtiRatio(0.55);

        loanApplication = new LoanApplication();
        loanApplication.setUser(testUser);
        loanApplication.setLoanAmountRequested(5000);
        loanApplication.setTerm(12);
        loanApplication.setPurpose("Education");
        loanApplication.setStatus(LoanStatus.PENDING);

        when(kieSession.insert(any())).thenReturn(mock(FactHandle.class));

        loanProcessingService.processLoanApplication(loanApplication, creditScore);

        verify(kieSession, times(2)).insert(any());
        verify(kieSession).fireAllRules();

        assertEquals(LoanStatus.REJECTED, loanApplication.getStatus());
    }

    @Test
    void testRejectionDueToAlternativeCreditData() {
        // Change of address >= 3 within 3 years, Late rental payments >= 4 within 24 months, Late cell phone payments >= 1 within 12 months
        creditScore = new CreditScore();
        creditScore.setUser(testUser);
        creditScore.setCreditScore(620);
        creditScore.setDtiRatio(0.45);
        creditScore.setChangeOfAddress(4);
        creditScore.setChangeOfAddressPeriod(3);
        creditScore.setLateRentalPayments(5);
        creditScore.setLateRentalPaymentsPeriod(24);
        creditScore.setLateCellPhonePayments(2);
        creditScore.setLateCellPhonePaymentsPeriod(12);

        loanApplication = new LoanApplication();
        loanApplication.setUser(testUser);
        loanApplication.setLoanAmountRequested(5000);
        loanApplication.setTerm(12);
        loanApplication.setPurpose("Education");
        loanApplication.setStatus(LoanStatus.PENDING);

        when(kieSession.insert(any())).thenReturn(mock(FactHandle.class));

        loanProcessingService.processLoanApplication(loanApplication, creditScore);

        verify(kieSession, times(2)).insert(any());
        verify(kieSession).fireAllRules();

        assertEquals(LoanStatus.REJECTED, loanApplication.getStatus());
    }

    @Test
    void testAutoStructuringIncrementDownPayment() {
        // Test auto structuring rule for incrementing down payment
        creditScore = new CreditScore();
        creditScore.setUser(testUser);
        creditScore.setCreditScore(620);
        creditScore.setDtiRatio(0.45);

        loanApplication = new LoanApplication();
        loanApplication.setUser(testUser);
        loanApplication.setLoanAmountRequested(5000);
        loanApplication.setTerm(12);
        loanApplication.setPurpose("Education");
        loanApplication.setStatus(LoanStatus.MANUAL_REVIEW);

        when(kieSession.insert(any())).thenReturn(mock(FactHandle.class));

        loanProcessingService.processLoanApplication(loanApplication, creditScore);

        // Adjust according to your specific auto-structuring logic
        assertEquals(LoanStatus.APPROVED, loanApplication.getStatus());
        assertEquals(loanApplication.getLoanAmountRequested(), 4500); // Adjusted loan amount
        assertEquals(loanApplication.getTerm(), 24); // Adjusted term

        verify(kieSession, times(2)).insert(any());
        verify(kieSession).fireAllRules();
    }

    @Test
    void testAutoStructuringDecreaseMonthlyPayment() {
        // Test auto structuring rule for decreasing monthly payment
        creditScore = new CreditScore();
        creditScore.setUser(testUser);
        creditScore.setCreditScore(620);
        creditScore.setDtiRatio(0.45);

        loanApplication = new LoanApplication();
        loanApplication.setUser(testUser);
        loanApplication.setLoanAmountRequested(5000);
        loanApplication.setTerm(12);
        loanApplication.setPurpose("Education");
        loanApplication.setStatus(LoanStatus.MANUAL_REVIEW);

        when(kieSession.insert(any())).thenReturn(mock(FactHandle.class));

        loanProcessingService.processLoanApplication(loanApplication, creditScore);

        // Adjust according to your specific auto-structuring logic
        assertEquals(LoanStatus.APPROVED, loanApplication.getStatus());
        assertEquals(loanApplication.getLoanAmountRequested(), 6000); // Adjusted loan amount
        assertEquals(loanApplication.getTerm(), 36); // Adjusted term

        verify(kieSession, times(2)).insert(any());
        verify(kieSession).fireAllRules();
    }

    @Test
    void testAutoStructuringReduceInterestRate() {
        // Test auto structuring rule for reducing interest rate
        creditScore = new CreditScore();
        creditScore.setUser(testUser);
        creditScore.setCreditScore(620);
        creditScore.setDtiRatio(0.45);

        loanApplication = new LoanApplication();
        loanApplication.setUser(testUser);
        loanApplication.setLoanAmountRequested(5000);
        loanApplication.setTerm(12);
        loanApplication.setPurpose("Education");
        loanApplication.setStatus(LoanStatus.MANUAL_REVIEW);

        when(kieSession.insert(any())).thenReturn(mock(FactHandle.class));

        loanProcessingService.processLoanApplication(loanApplication, creditScore);

        // Adjust according to your specific auto-structuring logic
        assertEquals(LoanStatus.APPROVED, loanApplication.getStatus());
        assertEquals(loanApplication.getLoanAmountRequested(), 5500); // Adjusted loan amount
        assertEquals(loanApplication.getTerm(), 30); // Adjusted term

        verify(kieSession, times(2)).insert(any());
        verify(kieSession).fireAllRules();
    }
}
