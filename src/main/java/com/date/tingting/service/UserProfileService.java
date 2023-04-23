package com.date.tingting.service;

import com.date.tingting.domain.userProfile.UserProfileCustomRepositoryImpl;
import com.date.tingting.web.responseDto.UserProfileResponse;
import org.springframework.security.core.userdetails.User;
import com.date.tingting.domain.userProfile.UserProfile;
import com.date.tingting.domain.userProfile.UserProfileRepository;
import com.date.tingting.web.requestDto.UserProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final UserProfileCustomRepositoryImpl userProfileCustomRepositoryImpl;

    @Transactional
    public void createUserProfile(List<UserProfileRequest> userProfileRequestList, User user) {

        //todo
        //해당 방식으로 저장하게 되면, jpa session이 낭비되지 않을까
        for(UserProfileRequest userProfileRequest : userProfileRequestList){

                List<String> valueList = userProfileRequest.getValueList();

                for(String value : valueList){
                    UserProfile userProfile = UserProfile.builder()
                            .uuid(user.getUsername())
                            .topic(userProfileRequest.getTopic())
                            .value(value)
                            .build();

                    userProfileRepository.save(userProfile);
                }
        }
    }


    public List<UserProfileResponse> getUserProfile(User user) {

        //todo
        //SqlResultSetMapping 또는 다른 방식 이용하여 조회하기
        List<UserProfile> userProfileList = userProfileRepository.findByUuid(user.getUsername());

        HashMap<String, String> profileMap = new HashMap<>();

        for(UserProfile profile : userProfileList){
            profileMap.put(profile.getTopic(), "value");
        }


        List<UserProfileResponse> userProfileResponseList = new ArrayList<>();

        for (String key : profileMap.keySet()) {
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setTopic(key);
            userProfileResponse.setValueList(new ArrayList<>());

            for(UserProfile profile : userProfileList){
                if(key.equals(profile.getTopic())){
                    userProfileResponse.getValueList().add(profile.getValue());
                }
            }

            userProfileResponseList.add(userProfileResponse);
        }

        return userProfileResponseList;
    }


    @Transactional
    public void updateUserProfile(List<UserProfileRequest> userProfileRequestList, User user) {

        String uuid = user.getUsername();
        //토픽에 대한 유저 프로파일 지우기
        for(UserProfileRequest userProfileRequest : userProfileRequestList){

            userProfileCustomRepositoryImpl.deleteUserProfile(uuid, userProfileRequest.getTopic());

            List<String> valueList = userProfileRequest.getValueList();

            for(String value : valueList){
                UserProfile userProfile = UserProfile.builder()
                        .uuid(uuid)
                        .topic(userProfileRequest.getTopic())
                        .value(value)
                        .build();

                userProfileRepository.save(userProfile);
            }
        }
    }


}
