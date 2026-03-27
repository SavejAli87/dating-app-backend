package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.UserFilterRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.services.UserFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserFilterController {
    private final UserFilterService userFilterService;

    @PostMapping("/filter")
    public ResponseEntity<Page<User>> filter(@RequestBody UserFilterRequest request){
        return ResponseEntity.ok(userFilterService.filterUsers(request));
    }
}
