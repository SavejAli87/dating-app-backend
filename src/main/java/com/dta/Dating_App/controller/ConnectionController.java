package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.ActionRequestDTO;
import com.dta.Dating_App.DTO.SendRequestDTO;
import com.dta.Dating_App.entitys.ConnectionRequest;
import com.dta.Dating_App.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConnectionController {

    private final ConnectionService connectionService;

    // Send Request
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody SendRequestDTO request){
        return ResponseEntity.ok(
                connectionService.sendRequest(
                        request.getSenderId(),
                        request.getReceiverId()
                )
        );
    }

    // Accept Request
    @PutMapping("/accept")
    public ResponseEntity<String> accept(@RequestBody ActionRequestDTO request){
        return ResponseEntity.ok(
                connectionService.acceptRequest(
                        request.getRequestId(),
                        request.getUserId()
                )
        );
    }

    //Decline Request
    @PutMapping("/decline")
    public ResponseEntity<String> decline(@RequestBody ActionRequestDTO request){
        return ResponseEntity.ok(
                connectionService.declineRequest(
                        request.getRequestId(),
                        request.getUserId()
                )
        );
    }

    // Cancel Request
    @PutMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody ActionRequestDTO request){
        return ResponseEntity.ok(
                connectionService.cancelRequest(
                        request.getRequestId(),
                        request.getUserId()
                )
        );
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
