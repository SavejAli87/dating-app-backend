package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramController {

    private final TelegramService telegramService;

    // connect
    @PostMapping("/connect")
    public ResponseEntity<String> connect(
            @RequestParam Long userId,
            @RequestParam String username
    ){
        return ResponseEntity.ok(
                telegramService.connectTelegram(userId, username)
        );
    }

    // get link
    @GetMapping("/link")
    public ResponseEntity<String> getLink(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(telegramService.getTelegram(userId));
    }

    //disconnect
    @DeleteMapping("/disconnect")
    public ResponseEntity<String> disconnect(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(telegramService.disconnectTelegram(userId));
    }

}
