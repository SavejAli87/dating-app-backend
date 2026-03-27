package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.Support;
import com.dta.Dating_App.services.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SupportController {

    private final SupportService supportService;

    // create support ticket
    @PostMapping("/create")
    public ResponseEntity<String> createTicket(
            @RequestBody Map<String, String> body
            ){
        Long userId = Long.valueOf(body.get("userId"));
        String subject = body.get("Subject");
        String message = body.get("message");
        return ResponseEntity.ok(supportService.CreateTicket(userId,subject,message));
    }

    // Get my tickets
    @GetMapping("/my")
    public ResponseEntity<List<Support>> myTickets(@RequestParam Long userId){
        return ResponseEntity.ok(supportService.getMyTickets(userId));
    }

    // admin ; get Tickets by status
    @GetMapping("/status")
    public ResponseEntity<List<Support>> byStatus(
            @RequestParam String status
    ){
        return ResponseEntity.ok(supportService.getTicketByStatus(status));
    }

    // close ticket
    @PutMapping("/close/{ticketId}")
    public ResponseEntity<String>  close(
            @PathVariable Long ticketId
    ){
        return ResponseEntity.ok(supportService.closeTicket(ticketId));
    }
}
