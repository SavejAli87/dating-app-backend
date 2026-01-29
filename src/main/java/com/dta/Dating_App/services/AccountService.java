package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    public String softDelete(Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        user.setDelete(true);
        userRepository.save(user);
        return "Account deactivated";
    }

    public String hardDelete(Long userId){
        userRepository.deleteById(userId);
        return "Account permanently Deleted" ;
    }
}
