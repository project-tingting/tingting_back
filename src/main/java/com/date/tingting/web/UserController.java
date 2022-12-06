package com.date.tingting.web;

import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.response.ResponseService;
import com.date.tingting.service.UserProfileService;
import com.date.tingting.service.UserService;
import com.date.tingting.web.requestDto.*;
import com.date.tingting.web.responseDto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserProfileService userProfileService;

    @GetMapping("/user")
    public TingTingResponse getUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {

        //todo
        //user에서 Uuid 가져오기, 리스폰되는 데이터에서 비밀번호는 제외하기
        String userId = user.getUsername();
        Optional<User> userOptional = userRepository.findByUserId(userId);

        User userInfo  = userService.getUser(userOptional.get().getUuid());

        List<UserProfileResponse> userProfileList = userProfileService.getUserProfile(user);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",userInfo);
        resultMap.put("userProfileList",userProfileList);
        return responseService.getTingTingResponse(resultMap);
    }

    @GetMapping("/user/list")
    public TingTingResponse getUserList() {

        List<User> userList = userService.getUserList();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userList",userList);
        return responseService.getTingTingResponse(resultMap);
    }

    /**
     * 메일 인증 완료
     */
    @GetMapping("/user/confirm")
    public TingTingResponse confirmEmail(EmailAuthRequest emailAuthRequest) {
        // TODO: 2022/12/03  파라미터 검사 해주기
        userService.confirmEmail(emailAuthRequest);
        return responseService.getTingTingResponse("인증 완료");
    }

    /**
     * 해당 메일의 유저가 인증이 완료된 유저인지 확인하기
     */
    @GetMapping("/user/confirmcheck/{userEmail}")
    public TingTingResponse confirmCheck(@PathVariable String userEmail) {
        User user = userService.confirmCheck(userEmail);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",user);
        return responseService.getTingTingResponse(resultMap);
    }

    @PostMapping("/user/signup")
    public TingTingResponse singup(@RequestBody UserSignUp userSignUp) {
        userService.checkSignUpValue(userSignUp);
        String result = userService.signup(userSignUp);
        return responseService.getTingTingResponse("회원가입 완료 " + result);
    }

    @PostMapping("/user/login")
    public TingTingResponse login(@RequestBody UserLogin userLogin) {
        return responseService.getTingTingResponse(userService.login(userLogin));
    }

    @PostMapping("/user/logout")
    public TingTingResponse logout(@RequestBody UserLogout userLogout) {
        userService.logout(userLogout);
        return responseService.getTingTingResponse("로그아웃 완료");
    }

}
