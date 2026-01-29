package com.dta.Dating_App.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User reportedBy;

    @ManyToOne
    private User reportedUser;

    private String reason;
    private  String status; //Pending , Reviewed

    private LocalDateTime createdAt;
}
