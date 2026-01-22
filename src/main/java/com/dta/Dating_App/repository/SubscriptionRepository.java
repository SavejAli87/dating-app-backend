package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.SubscriptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionRequest , Long> {

    List<SubscriptionRequest> findByReceiverId(Long receiverId);
}
