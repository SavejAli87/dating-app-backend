package com.dta.Dating_App.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    // 🔐 AUTH FIELDS
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(unique = true)
    private String telegramUsername;

    // 🔗 RELATIONS
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @ManyToOne
    private Subscription subscriptions;
}