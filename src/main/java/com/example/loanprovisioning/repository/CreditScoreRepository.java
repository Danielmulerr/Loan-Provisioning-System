package com.example.loanprovisioning.repository;

import com.example.loanprovisioning.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    @Query("select c from CreditScore c where c.user.userId = ?1")
    Optional<CreditScore> findByUser_UserId(long userId);
}