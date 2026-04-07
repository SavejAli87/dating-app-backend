package com.dta.Dating_App.repository;


import com.dta.Dating_App.entitys.SubscriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionDetailsRepository extends JpaRepository<SubscriptionDetails, Integer> {

    Optional<SubscriptionDetails> findByType(String type);


}