package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.TermsAcceptanceRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final TermsAcceptanceRepository termsAcceptanceRepository;

    public String softDelete(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setIsDeleted(true);

        userRepository.save(user); //  ADD THIS

        return "Account deactivated";
    }

    //  HARD DELETE
    @Transactional
    public String hardDelete(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        termsAcceptanceRepository.deleteByUserId(userId);

        userRepository.delete(user);

        return "Account permanently deleted";
    }
    //  ACTIVATE
    public String activateAccount(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(Boolean.FALSE.equals(user.getIsDeleted())){
            return "Account already active";
        }

        user.setIsDeleted(false);
        userRepository.save(user);

        return "Account activated successfully";
    }
}