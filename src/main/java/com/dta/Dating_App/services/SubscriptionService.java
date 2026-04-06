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

    public String sendRequest(String senderId, String receiverId){

        // ❌ Same user check
        if(senderId.equals(receiverId)){
            return "You cannot send request to yourself";
        }

        // ✅ Get sender
        User sender = userRepository.findByUserId(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // ✅ Get receiver
        User receiver = userRepository.findByUserId(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // 🔥 Create request
        SubscriptionRequest req = SubscriptionRequest.builder()
                .senderId(sender.getUserId())   // SA1000
                .receiverId(receiver.getUserId())
                .status("PENDING")
               // .createdAt(LocalDateTime.now())
                .build();

        subscriptionRequestRepository.save(req);

        return "Subscription request sent";
    }

    public String respond(Long requestId, String status){

        SubscriptionRequest req = subscriptionRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus(status);
        subscriptionRequestRepository.save(req);

        return "Request " + status + " ";
    }

    //Subscription Free, Gold, Premium

    public String activate(String userId, String plan){

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setPlan(plan);
        sub.setActive(true);
        sub.setStartDate(LocalDateTime.now());
        sub.setEndDate(LocalDateTime.now().plusMinutes(1));

        subscriptionRepository.save(sub);
        return "Subscription activated";
    }

    public Subscription getStatus(String userId){

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return subscriptionRepository.findByUserAndActiveTrue(user);
    }
}
