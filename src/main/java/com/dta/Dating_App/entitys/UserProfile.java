package com.dta.Dating_App.entitys;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 LINK WITH USER
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String displayName;
    private String gender;
    private String orientation;
    private Integer age;
    private String bio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String profileImageUrl;

    private String language;
    private String appearance;
    private String bodyType;
    private Integer height;
    private String englishLevel;
    private String ethnicity;
    private String lookingFor;
    private String smoke;
    private String drink;

    @Builder.Default
    private boolean verifiedSelfie = false;

    // 📍 LOCATION
    private String currentCity;
    private String currentState;
    private String currentCountry;

    private Double currentLat;
    private Double currentLng;

    // 🟢 STATUS
    @Builder.Default
    private Boolean online = false;

    private LocalDateTime lastSeen;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}