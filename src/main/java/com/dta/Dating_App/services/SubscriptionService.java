package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.Subscription;
import com.dta.Dating_App.entitys.SubscriptionRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.SubscriptionRepository;
import com.dta.Dating_App.repository.SubscriptionRequestRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRequestRepository subscriptionRequestRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public String sendRequest(Long senderId, Long receiverId){

        SubscriptionRequest req = SubscriptionRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status("PENDING")
                .build();

        subscriptionRequestRepository.save(req);
        return "Subscription request sent ";
    }

    public String respond(Long requestId, String status){

        SubscriptionRequest req = subscriptionRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus(status);
        subscriptionRequestRepository.save(req);

        return "Request " + status + " ";
    }

    //Subscription Free, Gold, Premium

    public String activate(Long userId, String plan){

        User user =userRepository.findById(userId).orElseThrow();

        Subscription sub= new Subscription();
        sub.setUser(user);
        sub.setPlan(plan);
        sub.setActive(true);
        sub.setStartDate(LocalDateTime.now());
        sub.setEndDate(LocalDateTime.now().plusMinutes(1));

        subscriptionRepository.save(sub);
        return "Subscription activated";
    }

    public Subscription getStatus(Long userId){
        return subscriptionRepository.findByUserIdAndActiveTrue(userId);
    }
}
