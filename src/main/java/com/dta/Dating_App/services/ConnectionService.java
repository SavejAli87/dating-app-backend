package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.ConnectionRequest;
import com.dta.Dating_App.entitys.RequestStatus;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.ConnectionRequestRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRequestRepository requestRepository;
    private final UserRepository userRepository;

    // Send Request
    public String sendRequest(Long senderId, Long receiverId){

        if (senderId.equals(receiverId)){
            return "You cannot send request to yourself";

        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // if receiver already sent request to sender then accept directly (optional)
        requestRepository.findBySenderIdAndReceiverId(receiverId, senderId).ifPresent(existing -> {
            if (existing.getStatus() == RequestStatus.PENDING){
                existing.setStatus(RequestStatus.ACCEPTED);
                existing.setUpdatedAt(LocalDateTime.now());
                requestRepository.save(existing);
                throw new RuntimeException("Already request received , auto accepted");
            }
        });

        ConnectionRequest request  = ConnectionRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(RequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        requestRepository.save(request);

        // Notification can be triggered here
        return "Request sent successfully";
    }
    // Accept Request

    public String acceptRequest(Long requestId, Long receiverId){
        ConnectionRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if(!request.getReceiver().getId().equals(receiverId)){
            return "You cannot accept this request";
        }

        request.setStatus(RequestStatus.ACCEPTED);
        request.setUpdatedAt(LocalDateTime.now());
        requestRepository.save(request);

        // Notification : "Request accepted"
        return "Request accepted ";
    }

    // Decline Request
    public String declineRequest(Long requestId, Long receiverId){

        ConnectionRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if(!request.getReceiver().getId().equals(receiverId)){
            return "You cannot decline this request";
        }

        if(request.getStatus() != RequestStatus.PENDING){
            return "Request is not pending";
        }

        request.setStatus(RequestStatus.DECLINED);
        request.setUpdatedAt(LocalDateTime.now());
        requestRepository.save(request);

        return "Request declined";
    }

    // Cancel Request ( Sender cancels )
    public String cancelRequest(Long requestId, Long senderId){

        ConnectionRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getSender().getId().equals(senderId)){
            return "You cannot cancel this request ";

        }
        if (request.getStatus() != RequestStatus.PENDING) {
            return "Request cannot be cancelled";
        }

        request.setStatus(RequestStatus.CANCELLED);
        request.setUpdatedAt(LocalDateTime.now());
        requestRepository.save(request);

        return "Request cancelled";
    }

    // Received List (Pending)
    public List<ConnectionRequest> getReceiverRequests(Long userId){
        return requestRepository.findByReceiverIdAndStatus(userId, RequestStatus.PENDING);

    }

    // sent List (Pending )
    public List<ConnectionRequest> getSentRequests(Long userId){
        return requestRepository.findBySenderIdAndStatus(userId, RequestStatus.PENDING);
    }

    // Accepted Connection List
    public List<ConnectionRequest> getConnection(Long userId){
        return requestRepository.findBySenderIdAndStatusOrReceiverIdAndStatus(
                userId, RequestStatus.ACCEPTED,
                userId, RequestStatus.ACCEPTED
        );
    }

    // Relationship status ( important API)
    public String getRelationshipStatus(Long user1, Long user2){

        return requestRepository.findBySenderIdAndReceiverId(user1, user2)
                .map(r -> r.getStatus().name())
                .orElseGet(() -> requestRepository.findBySenderIdAndReceiverId(user1,user2)
                        .map(r -> r.getStatus().name())
                        .orElse("NONE"));
    }
}
