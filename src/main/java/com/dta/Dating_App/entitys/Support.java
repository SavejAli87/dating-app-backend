package com.dta.Dating_App.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_support")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId",nullable = false)
    private User user;

    private String subject;
    private String message;
    private String status; //open, closed

    private LocalDateTime createdAt;
}
