package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByMobile(String mobile);

    Optional<User> findByUserId(String userId);

    boolean existsByMobile(String mobile);

    //  FIXED QUERY
    @Query("""
SELECT u FROM User u
JOIN u.profile p
WHERE (:gender IS NULL OR LOWER(p.gender) = LOWER(:gender))
AND (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
AND (:minAge IS NULL OR p.age >= :minAge)
AND (:maxAge IS NULL OR p.age <= :maxAge)
AND (:language IS NULL OR LOWER(p.language) = LOWER(:language))
AND (:ethnicity IS NULL OR LOWER(p.ethnicity) = LOWER(:ethnicity))
AND (:smoke IS NULL OR LOWER(p.smoke) = LOWER(:smoke))
AND (:drink IS NULL OR LOWER(p.drink) = LOWER(:drink))
""")
    List<User> searchUsers(
            String gender,
            String name,
            Integer minAge,
            Integer maxAge,
            String language,
            String ethnicity,
            String smoke,
            String drink
    );

    boolean existsByTelegramUsername(String telegramUsername);

    User findTopByOrderByIdDesc();




}