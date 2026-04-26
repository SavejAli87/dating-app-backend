package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.Subscriber;
import com.dta.Dating_App.entitys.SubscriptionDetails;
import com.dta.Dating_App.repository.SubscriberRepository;
import com.dta.Dating_App.repository.SubscriptionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriptionDetailsRepository detailsRepository;

    //  Activate Plan
    public String activatePlan(String userId, String type) {

        SubscriptionDetails details = detailsRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscriber sub = new Subscriber();

        sub.setUserId(userId);
        sub.setType(details.getType());
        sub.setPrice(details.getAmount());
        sub.setContactView(Long.valueOf(details.getContactView()));
        sub.setDuration(details.getDuration());

        sub.setStartDate(LocalDate.now());
        sub.setEndDate(LocalDate.now().plusDays(details.getDuration()));

        sub.setStartTime(LocalTime.now());
        sub.setEndTime(LocalTime.now());

        sub.setEventType("PLAN_ACTIVATED");
        sub.setStatus("ACTIVE");
        sub.setSubscriptionId(UUID.randomUUID().toString());

        sub.setDescription(details.getDescription());
        sub.setPlanType(details.getActivePlaneName());
        sub.setRemainsDays(details.getDuration());

        subscriberRepository.save(sub);

        return "Plan Activated Successfully";
    }

    //  Get Active Plan
    public Subscriber getUserPlan(String userId) {
        return subscriberRepository
                .findTopByUserIdOrderByEndDateDesc(userId)
                .orElseThrow(() -> new RuntimeException("No active plan found"));
    }

    //  Check Remaining Days
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRemainingDays() {

        List<Subscriber> list = subscriberRepository.findAll();

        for (Subscriber sub : list) {

            long days = ChronoUnit.DAYS.between(LocalDate.now(), sub.getEndDate());

            sub.setRemainsDays((int) Math.max(days, 0));

            subscriberRepository.save(sub);
        }
    }

    public Integer getRemainingDays(String userId) {

        Subscriber sub = subscriberRepository
                .findTopByUserIdOrderByEndDateDesc(userId)
                .orElseThrow(() -> new RuntimeException("No active plan"));

        if (sub.getEndDate() == null) {
            return 0;
        }

        long days = ChronoUnit.DAYS.between(LocalDate.now(), sub.getEndDate());

        return (int) Math.max(days, 0);
    }
}