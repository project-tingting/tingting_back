package com.date.tingting.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

    User findByUserMail(String userMail);

}
