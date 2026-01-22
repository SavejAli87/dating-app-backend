package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.SubscriptionRequest;
import com.dta.Dating_App.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public String sendRequest(Long senderId, Long receiverId){

        SubscriptionRequest req = SubscriptionRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status("PENDING")
                .build();

        subscriptionRepository.save(req);
        return "Subscription request sent ";
    }

    public String respond(Long requestId, String status){

        SubscriptionRequest req = subscriptionRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus(status);
        subscriptionRepository.save(req);

        return "Request " + status + " ";
    }
}
