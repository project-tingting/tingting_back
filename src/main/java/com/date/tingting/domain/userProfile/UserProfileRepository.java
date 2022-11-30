package com.date.tingting.domain.userProfile;

import com.date.tingting.web.requestDto.UserProfileRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileRequest, String> {

}
