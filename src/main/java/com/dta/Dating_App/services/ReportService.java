package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserReport;
import com.dta.Dating_App.repository.UserReportRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserReportRepository userReportRepository;
    private final UserRepository userRepository;

    // Report a user

    public String reportUser(Long byUserId, Long targetUserId, String reason){
        if (byUserId.equals(targetUserId)){
            return "You cannot report yourself";
        }

        User reportedBy = userRepository.findById(byUserId)
                .orElseThrow(() -> new RuntimeException("Reporting user not found"));

        User reportedUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        // Prevent duplicate report

        boolean alreadyReported = userReportRepository.existsByReportedByIdAndReportedUserId(byUserId, targetUserId);

        if (alreadyReported) {
            return "You have already reported this user";
        }

        UserReport report = UserReport.builder()
                .reportedBy(reportedBy)
                .reportedUser(reportedUser)
                .reason(reason)
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .build();

        userReportRepository.save(report);

        return "User reported successfully";
    }

    //  Reports against a user (Admin)
    public List<UserReport> getReportsAgainstUser(Long userId) {
        return userReportRepository.findByReportedUserIdOrderByCreatedAtDesc(userId);
    }

    //  Reports created by a user
    public List<UserReport> getMyReports(Long userId) {
        return userReportRepository.findByReportedByIdOrderByCreatedAtDesc(userId);
    }

    //  Resolve report (Admin)
    public String resolveReport(Long reportId, String status) {

        UserReport report = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setStatus(status); // RESOLVED / REJECTED
        userReportRepository.save(report);

        return "Report marked as " + status + "  ";
    }

}
