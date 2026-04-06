package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.SupportRequestDTO;
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

    @PostMapping("/create")
    public ResponseEntity<String> createTicket(
            @RequestBody SupportRequestDTO request
    ){
        return ResponseEntity.ok(
                supportService.CreateTicket(
                        request.getUserId(),
                        request.getSubject(),
                        request.getMessage()
                )
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<Support>> myTickets(@RequestParam Long userId){
        return ResponseEntity.ok(supportService.getMyTickets(userId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Support>> byStatus(@RequestParam String status){
        return ResponseEntity.ok(supportService.getTicketByStatus(status));
    }

    @PutMapping("/close/{ticketId}")
    public ResponseEntity<String> close(@PathVariable Long ticketId){
        return ResponseEntity.ok(supportService.closeTicket(ticketId));
    }
}