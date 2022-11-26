package com.date.tingting.domain.emailAuth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
//    Optional<EmailAuth> findValidAuthByEmail(String userEmail, String authToken, LocalDateTime currentTime);
}
