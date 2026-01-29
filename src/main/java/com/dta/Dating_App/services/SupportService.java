package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.Support;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.SupportRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {

    private final SupportRepository supportRepository;
    private final UserRepository userRepository;

    public String CreateTicket(Long userId, String subject, String message){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Support t= Support.builder()
                 .user(user)
                .subject(subject)
                .message(message)
                .status("open")
                .createdAt(LocalDateTime.now())
                .build();

        supportRepository.save(t);
        return "Support ticket submitted";
    }

    // get all tickets of a user
    public List<Support> getMyTickets(Long userId){
        return supportRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Admin: get tickets by status
    public List<Support> getTicketByStatus(String status){
        return supportRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    // close ticket (user/admin)
    public String closeTicket(Long ticketId){

        Support t=supportRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if ("Closed".equalsIgnoreCase(t.getStatus())){
            return "Ticket already closed";
        }

        t.setStatus("CLOSED");
        supportRepository.save(t);

        return "Ticket closed successfully";
    }

}
