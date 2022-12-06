package com.date.tingting.domain.userProfile;


import com.date.tingting.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    List<UserProfile> findByUuid(String uuid);

}
