package com.example.loanprovisioning.repository;

import com.example.loanprovisioning.entity.LoanRepayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Long> {
    @Query("select l from LoanRepayment l where l.loanApplication.loanApplicationId = ?1")
    List<LoanRepayment> findByLoanApplication_LoanApplicationId(Long loanApplicationId);
}