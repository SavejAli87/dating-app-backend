package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.TermsAcceptance;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.TermsAcceptanceRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrivacyService {

    private final TermsAcceptanceRepository repo;
    private final UserRepository userRepository;


    public String acceptTerms(Long userId){

        if (repo.existsByUserId(userId)){
            return "Terms already accepted";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TermsAcceptance t = TermsAcceptance.builder()
                .user(user)
                .acceptedAt(LocalDateTime.now())
                .build();

        repo.save(t);

        return "Terms accepted successfully";
    }

    // check if user accepted terms
    public boolean hasAcceptedTerms(Long userId){
        return repo.existsByUserId(userId);
    }

    // get acceptance details (Optional)
    public TermsAcceptance getAcceptance(Long userId){
        return repo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Terms not accepted yet"));
    }
}
