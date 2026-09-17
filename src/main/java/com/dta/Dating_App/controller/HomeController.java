package com.dta.Dating_App.controller;


import com.dta.Dating_App.DTO.UserCardDTO;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.services.HomePage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class HomeController {

    private final HomePage homePage;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getHomeUsers(@PathVariable String userId) {

        List<UserCardDTO> users = homePage.getOppositeUsers(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("count", users.size());
        response.put("data", users);

        return ResponseEntity.ok(response);
    }
}