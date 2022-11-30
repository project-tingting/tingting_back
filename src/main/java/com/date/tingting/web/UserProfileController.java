package com.date.tingting.web;

import com.date.tingting.domain.user.User;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.UserProfileService;
import com.date.tingting.service.UserService;
import com.date.tingting.web.requestDto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TingTingResponse createUserProfile(@RequestBody List<UserProfileRequest> userProfileRequestList) {
        userProfileService.createUserProfile(userProfileRequestList);
        return responseService.getTingTingResponse("회원가입 완료 ");
    }

}
