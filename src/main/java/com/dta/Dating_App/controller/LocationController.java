package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.LocationRequest;
import com.dta.Dating_App.entitys.UserLocation;
import com.dta.Dating_App.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocationController {

    private final LocationService locationService;

    // current location
    @GetMapping("/current/{userId}")
    public ResponseEntity<UserLocation> current(@PathVariable Long userId){
        return ResponseEntity.ok(locationService.getCurrentLocation(userId));
    }

    // location history
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<UserLocation>> history(@PathVariable Long userId){
        return ResponseEntity.ok(locationService.getLocationHistory(userId));
    }

    // Add new location + set current
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody LocationRequest request){
        return ResponseEntity.ok(locationService.addNewLocation(request));

    }

    // Switch back location
    @PutMapping("/switch")
    public ResponseEntity<String> switchLocation(@RequestParam Long userId,
                                                 @RequestParam Long locationId){
        return ResponseEntity.ok(locationService.switchLocation(userId, locationId));
    }
}
