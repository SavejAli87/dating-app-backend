package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);


}
