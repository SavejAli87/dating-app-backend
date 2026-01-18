package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    List<UserImage> findByUserId(Long userId);

}
