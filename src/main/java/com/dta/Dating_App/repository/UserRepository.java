package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    //Optional<User> findByMobile(String mobile);
    Optional<User> findByMobile(String mobile);

    boolean existsByDisplayName(String displayName);

    @Query("""
             SELECT u FROM User u
                   WHERE (:minAge IS NULL OR u.age >= :minAge)
                   AND (:maxAge IS NULL OR u.age <= :maxAge)
                   AND (:language IS NULL OR u.language = :language)
                   AND (:ethnicity IS NULL OR u.ethnicity = :ethnicity)
                   AND (:smoke IS NULL OR u.smoke = :smoke)
                   AND (:drink IS NULL OR u.drink = :drink)
            """)
    List<User> searchUsers(Integer minAge, Integer maxAge,
                          String language, String ethnicity,
                          String smoke, String drink);

    //Telegram
    boolean existsByTelegramUsername(String telegramUsername);
}
