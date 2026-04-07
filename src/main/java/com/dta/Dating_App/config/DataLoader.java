package com.dta.Dating_App.config;

import com.dta.Dating_App.entitys.SubscriptionDetails;
import com.dta.Dating_App.repository.SubscriptionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SubscriptionDetailsRepository repo;

    @Override
    public void run(String... args) {

        if(repo.count() == 0){   // duplicate avoid

            SubscriptionDetails free = new SubscriptionDetails();
            free.setAmount(0);
            free.setContactView("10");
            free.setType("FREE");
            free.setDuration(7);
            free.setDescription("Free Plan");
            free.setActivePlaneName("FREE_PLAN");

            SubscriptionDetails gold = new SubscriptionDetails();
            gold.setAmount(199);
            gold.setContactView("50");
            gold.setType("GOLD");
            gold.setDuration(30);
            gold.setDescription("Gold Plan");
            gold.setActivePlaneName("GOLD_PLAN");

            SubscriptionDetails premium = new SubscriptionDetails();
            premium.setAmount(499);
            premium.setContactView("100");
            premium.setType("PREMIUM");
            premium.setDuration(90);
            premium.setDescription("Premium Plan");
            premium.setActivePlaneName("PREMIUM_PLAN");

            repo.save(free);
            repo.save(gold);
            repo.save(premium);
        }
    }
}