package com.example.loanprovisioning.repository;

import com.example.loanprovisioning.entity.LoanApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    @Query("select l from LoanApplication l where l.applicationDate between ?1 and ?2")
    Page<LoanApplication> findByApplicationDateBetween(Timestamp applicationDateStart, Timestamp applicationDateEnd, Pageable pageable);
}