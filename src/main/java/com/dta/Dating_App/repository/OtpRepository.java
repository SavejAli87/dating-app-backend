package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.OtpType;
import com.dta.Dating_App.entitys.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpVerification,Long> {
    Optional<OtpVerification> findByEmail(String email, OtpType otpType);
}
