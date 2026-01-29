package com.dta.Dating_App.entitys;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder   //  REQUIRED FOR builder()
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
   // private String email;
    private String mobile;
    private String password;
    private String gender;
    private String orientation;
    private Integer age;
    private String bio;
    private String role;


    @Column(nullable = false)
    private String displayName;

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
    private String smoke;   // YES / NO / OCCASIONALLY
    private String drink;   // YES / NO / OCCASIONALLY
    private boolean verifiedSelfie; //  selfie verification status

    // Location
    private String currentCity;
    private String currentState;
    private String currentCountry;

    private Double currentLat;
    private Double currentLng;

    private Boolean online;
    private java.time.LocalDateTime lastSeen;
    private java.time.LocalDateTime createdAt;

    private boolean delete;

}
