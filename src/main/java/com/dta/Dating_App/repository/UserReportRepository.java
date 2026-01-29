package com.dta.Dating_App.repository;


import com.dta.Dating_App.entitys.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    // Reports against a particular user
    List<UserReport> findByReportedUserIdOrderByCreatedAtDesc(Long userId);

    // Reports created by a user
    List<UserReport> findByReportedByIdOrderByCreatedAtDesc(Long userId);

    // Reports by status (pending / resolved)
    List<UserReport> findByStatusOrderByCreatedAtDesc(String status);

    // Optional
    boolean existsByReportedByIdAndReportedUserId(Long byUserId, Long targetUserId);
}
