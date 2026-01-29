package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support, Long> {

    // Get All tickets of a user ( latest first )
    List<Support> findByUserIdOrderByCreatedAtDesc(Long userId);

    // optional: get tickets by status (open/ closed )
    List<Support> findByStatusOrderByCreatedAtDesc(String status);
}
