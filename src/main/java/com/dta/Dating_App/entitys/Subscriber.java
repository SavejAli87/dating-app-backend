package com.dta.Dating_App.entitys;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name ="subscriber")
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private int price;
    private Long contactView;
    private String type; // Free / Gold / Premium
    private int duration;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private String eventType;
    private String subscriptionId;
    private String cuponCode;

    private Integer remainsDays;
    private String description;
    private String planType;

    private String status; // ACTIVE / EXPIRED
}