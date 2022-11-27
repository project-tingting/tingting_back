package com.date.tingting.domain.user;

import com.date.tingting.domain.emailAuth.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUuid(String uuid);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserEmail(String userEmail);
    List<User> findAll();

    boolean existsByUserId(String userId);

}
