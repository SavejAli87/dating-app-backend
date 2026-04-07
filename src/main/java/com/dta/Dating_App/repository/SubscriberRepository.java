package com.dta.Dating_App.repository;


import com.dta.Dating_App.entitys.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Optional<Subscriber> findByUserIdAndPlanType(String userId, String planType);

    Optional<Subscriber> findByUserId(String userId);


}