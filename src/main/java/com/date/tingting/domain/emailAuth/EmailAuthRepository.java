package com.date.tingting.domain.emailAuth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {

    void deleteByUserEmail(String userId);

}
