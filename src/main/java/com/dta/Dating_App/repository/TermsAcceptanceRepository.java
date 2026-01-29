package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.TermsAcceptance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermsAcceptanceRepository extends JpaRepository<TermsAcceptance, Long> {

    //check if user already accepted terms
    Optional<TermsAcceptance> findByUserId(Long userId);

    //Optional helper
    boolean existsByUserId(Long userId);
}
