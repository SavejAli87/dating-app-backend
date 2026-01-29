package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.Notification;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.NotificationRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    //  Push notification
    public void push(Long userId, String msg) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification n = Notification.builder()
                .user(user)
                .message(msg)
                .readStatus(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(n);
    }

    //  Get all notifications
    public List<Notification> getAll(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Mark as read
    public void markRead(Long notificationId) {

        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!n.isReadStatus()) {
            n.setReadStatus(true);
            notificationRepository.save(n);
        }
    }
}

