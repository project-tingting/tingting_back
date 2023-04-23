package com.date.tingting.web;

import com.date.tingting.domain.tingTingToken.TingTingToken;
import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.response.ResponseService;
import com.date.tingting.service.TingTingTokenService;
import com.date.tingting.service.UserProfileService;
import com.date.tingting.service.UserService;
import com.date.tingting.web.requestDto.*;
import com.date.tingting.web.responseDto.UserProfileResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private final TingTingTokenService tingTingTokenService;

    @GetMapping("/user")
    @ApiOperation(value="유저 정보 조회", notes="유저 정보 조회 API")
    public TingTingResponse getUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {


        User userInfo  = userService.getUser(user.getUsername());

        List<UserProfileResponse> userProfileList = userProfileService.getUserProfile(user);

        TingTingToken tingTingToken = tingTingTokenService.getTingTingTokenInfo(user.getUsername());

        //todo
        //user에서 Uuid 가져오기, 리스폰되는 데이터에서 비밀번호는 제외하기

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",userInfo);
        resultMap.put("userProfileList",userProfileList);
        resultMap.put("tingTingToken",tingTingToken);

        return responseService.getTingTingResponse(resultMap);
    }

    @GetMapping("/user/list")
    @ApiOperation(value="유저 정보 리스트 조회", notes="유저 정보 리스트 조회 API")
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
    @ApiOperation(value="이메일 인증 완료", notes="이메일 인증 완료 API")
    public TingTingResponse confirmEmail(EmailAuthRequest emailAuthRequest) {
        // TODO: 2022/12/03  파라미터 검사 해주기
        userService.confirmEmail(emailAuthRequest);
        return responseService.getTingTingResponse("인증 완료");
    }

    /**
     * 해당 메일의 유저가 인증이 완료된 유저인지 확인하기
     */
    @GetMapping("/user/confirmcheck/{userEmail}")
    @ApiOperation(value="인증이 완료됬는지 확인", notes="해당 메일의 유저가 인증이 완료된 유저인지 확인하기")
    @ApiImplicitParam(name = "userEmail", value = "유저 이메일")
    public TingTingResponse confirmCheck(@PathVariable String userEmail) {
        User user = userService.confirmCheck(userEmail);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", user);
        return responseService.getTingTingResponse(resultMap);
    }

    @PostMapping("/user/signup")
    @ApiOperation(value="회원가입", notes="회원가입 API")
    public TingTingResponse singup(@RequestBody UserSignUp userSignUp) {
        userService.checkSignUpValue(userSignUp);
        String result = userService.signup(userSignUp);
        return responseService.getTingTingResponse("회원가입 완료 " + result);
    }

    @PostMapping("/user/login")
    @ApiOperation(value="로그인", notes="로그인 API")
    public TingTingResponse login(@RequestBody UserLogin userLogin) {
        return responseService.getTingTingResponse(userService.login(userLogin));
    }

    @PostMapping("/user/logout")
    @ApiOperation(value="로그아웃", notes="로그아웃 API")
    public TingTingResponse logout(@RequestBody UserLogout userLogout) {
        userService.logout(userLogout);
        return responseService.getTingTingResponse("로그아웃 완료");
    }

}
