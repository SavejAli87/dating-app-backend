package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramService {

    private final UserRepository userRepository;

    //Add or update Telegram
    public String connectTelegram(Long userId, String username){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // remove @ if user sends it
        username = username.replace("@","");

        if(userRepository.existsByTelegramUsername(username)){
            throw new RuntimeException("Telegram username already used");
        }
        user.setTelegramUsername(username);
        userRepository.save(user);

        return "Telegram connected";
    }

    //Get telegram link
    public String getTelegram(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getTelegramUsername() == null){
            return "Telegram not connected";
        }

        return "https://t.me/" + user.getTelegramUsername();
    }

    //Remove telegram
    public String disconnectTelegram(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setTelegramUsername(null);
        userRepository.save(user);

        return "Telegram disconnected";
    }

}
