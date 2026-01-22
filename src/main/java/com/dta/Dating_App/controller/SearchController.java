package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.SearchFilterRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @PostMapping
    public ResponseEntity<List<User>> searchUsers(@RequestBody SearchFilterRequest request){
        return ResponseEntity.ok(searchService.search(request));
    }
}
