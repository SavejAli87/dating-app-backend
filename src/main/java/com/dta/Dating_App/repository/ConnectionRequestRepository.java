package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.ConnectionRequest;
import com.dta.Dating_App.entitys.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    // sender -> receiver ( unique request )
    Optional<ConnectionRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // received list
    List<ConnectionRequest> findByReceiverIdAndStatus(Long receiverId, RequestStatus status);

    // sent list
    List<ConnectionRequest> findBySenderIdAndStatus(Long senderId, RequestStatus status);

    // accepted connections list ( for a user )
    List<ConnectionRequest> findBySenderIdAndStatusOrReceiverIdAndStatus(
            Long senderId, RequestStatus status1,
            Long receiverId, RequestStatus status2
    );
}
