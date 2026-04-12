package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.SearchFilterRequest;
import com.dta.Dating_App.DTO.UserSearchResponse;
import com.dta.Dating_App.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    public ResponseEntity<List<UserSearchResponse>> searchUsers(@RequestBody SearchFilterRequest request){
        return ResponseEntity.ok(searchService.search(request));
    }
}