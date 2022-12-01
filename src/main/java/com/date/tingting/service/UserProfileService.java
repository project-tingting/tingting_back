package com.date.tingting.service;

import com.date.tingting.domain.user.UserRepository;
import org.springframework.security.core.userdetails.User;
import com.date.tingting.domain.userProfile.UserProfile;
import com.date.tingting.domain.userProfile.UserProfileRepository;
import com.date.tingting.web.requestDto.UserProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {


    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public void createUserProfile(List<UserProfileRequest> userProfileRequestList, User user) {

        String userId = user.getUsername();

        Optional<com.date.tingting.domain.user.User> userInfo = userRepository.findByUserId(userId);

        for(UserProfileRequest userProfileRequest : userProfileRequestList){
            userProfileRequest.setUuid(userInfo.get().getUuid());
        }

        List<UserProfile> commentList = UserProfile.of(userProfileRequestList);
        userProfileRepository.saveAll(commentList);
    }


}
