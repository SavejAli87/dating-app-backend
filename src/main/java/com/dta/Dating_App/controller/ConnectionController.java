package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.ConnectionRequest;
import com.dta.Dating_App.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    // Send Request
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam Long senderId,
                                       @RequestParam Long receiverId){
        return ResponseEntity.ok(connectionService.sendRequest(senderId,receiverId));
    }

    // Accept Request
    @PutMapping("/accept")
    public ResponseEntity<String> accept(@RequestParam Long requestId,
                                         @RequestParam Long receiverId){
        return ResponseEntity.ok(connectionService.acceptRequest(requestId,receiverId));
    }

    //Decline Request
    @PutMapping("/decline")
    public ResponseEntity<String> decline(@RequestParam Long requestId,
                                          @RequestParam Long receiverId){
        return ResponseEntity.ok(connectionService.declineRequest(requestId, receiverId));
    }

    // Cancel Request
    @PutMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestParam Long requestId,
                                         @RequestParam Long senderId){
        return ResponseEntity.ok(connectionService.cancelRequest(requestId, senderId));
    }

    // Received List
    @GetMapping("/received")
    public ResponseEntity<List<ConnectionRequest>> received(@RequestParam Long userId){
        return ResponseEntity.ok(connectionService.getReceiverRequests(userId));

    }

    // sent list
    @GetMapping("/sent")
    public ResponseEntity<List<ConnectionRequest>> sent (@RequestParam Long userId) {
        return ResponseEntity.ok(connectionService.getSentRequests(userId));
    }

    // All accepted connection
    @GetMapping("/list")
    public ResponseEntity<List<ConnectionRequest>> connections(@RequestParam  Long userId ){
        return ResponseEntity.ok(connectionService.getConnection(userId));
    }

    // Relationship status API
    @GetMapping("/status")
    public ResponseEntity<String> status(@RequestParam Long user1,
                                         @RequestParam Long user2){
        return ResponseEntity.ok(connectionService.getRelationshipStatus(user1, user2));
    }
}
