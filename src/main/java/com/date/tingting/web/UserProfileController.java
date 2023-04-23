package com.date.tingting.web;

import com.date.tingting.domain.userProfile.UserProfile;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.UserProfileService;
import com.date.tingting.web.requestDto.*;
import com.date.tingting.web.responseDto.UserProfileResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserProfileController {

    @Autowired
    private final UserProfileService userProfileService;

    @Autowired
    private final ResponseService responseService;

    @PostMapping("/userprofile")
    @ApiOperation(value="유저 프로파일 생성", notes="유저 프로파일 생성 API")
    public TingTingResponse createUserProfile(@RequestBody List<UserProfileRequest> userProfileRequestList, @AuthenticationPrincipal User user) {

        userProfileService.createUserProfile(userProfileRequestList, user);
        return responseService.getTingTingResponse("유저 프로퍼티 생성 완료");
    }

    @GetMapping("/userprofile")
    @ApiOperation(value="유저 프로파일 조회", notes="유저 프로파일 조회 API")
    public TingTingResponse getUserProfile(@AuthenticationPrincipal User user) {

        List<UserProfileResponse> userProfileList = userProfileService.getUserProfile(user);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userProfileList",userProfileList);
        return responseService.getTingTingResponse(resultMap);
    }

    @PutMapping("/userprofile")
    @ApiOperation(value="유저 프로파일 수정", notes="유저 프로파일 수정 API")
    public TingTingResponse updateUserProfile(@RequestBody List<UserProfileRequest> userProfileRequestList, @AuthenticationPrincipal User user) {

        userProfileService.updateUserProfile(userProfileRequestList, user);
        return responseService.getTingTingResponse("유저 프로퍼티 수정 완료");
    }

}

