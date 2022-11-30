package com.date.tingting.service;

import com.date.tingting.domain.userProfile.UserProfileRepository;
import com.date.tingting.web.requestDto.UserProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {


    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void createUserProfile(List<UserProfileRequest> userProfileRequestList) {

        userProfileRepository.saveAll(userProfileRequestList);
    }


}
