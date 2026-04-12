package com.dta.Dating_App.scheduler;

import com.dta.Dating_App.entitys.Subscriber;
import com.dta.Dating_App.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionScheduler {

    private final SubscriberRepository subscriberRepository;

    // 🔥 Daily run (midnight)
    @Scheduled(cron = "0 0 0 * * ?")
    public void expirePlans() {

        List<Subscriber> list = subscriberRepository.findAll();

        for (Subscriber sub : list) {

            if ("ACTIVE".equalsIgnoreCase(sub.getStatus())
                    && sub.getEndDate().isBefore(LocalDate.now())) {

                sub.setStatus("EXPIRED");
                sub.setRemainsDays(0);

                subscriberRepository.save(sub);
            }
        }
    }
}