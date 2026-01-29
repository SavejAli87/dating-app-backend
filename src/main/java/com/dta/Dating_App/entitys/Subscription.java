package com.dta.Dating_App.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    private User user;

    private String plan;//Free, Gold, Premium
    private boolean active;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
