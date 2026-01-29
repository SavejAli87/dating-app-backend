package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByUserIdAndActiveTrue(Long userId);
}
