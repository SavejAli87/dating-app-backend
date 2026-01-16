package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
}
