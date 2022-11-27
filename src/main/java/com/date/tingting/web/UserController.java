package com.date.tingting.web;

import com.date.tingting.domain.user.User;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.response.ResponseService;
import com.date.tingting.service.UserService;
import com.date.tingting.web.requestDto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    @GetMapping("/user/{uuid}")
    public TingTingResponse getUser(@PathVariable String uuid) {

        User user = userService.findByUuid(uuid);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",user);
        return responseService.getTingTingResponse(resultMap);
    }

    @GetMapping("/user/list")
    public TingTingResponse getUserList() {

        List<User> userList = userService.findAll();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userList",userList);
        return responseService.getTingTingResponse(resultMap);
    }

    @PostMapping("/user/register")
    public TingTingResponse createUser(@RequestBody UserRequest userRequest){

        userRequest.validate();
        String uuid = userService.save(userRequest);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("uuid",uuid);
        return responseService.getTingTingResponse(resultMap);
    }

    @GetMapping("/user/confirme")
    public TingTingResponse confirmEmail(EmailAuthRequest emailAuthRequest) {
        userService.confirmEmail(emailAuthRequest);
        return responseService.getTingTingResponse("인증 완료");
    }

    @PostMapping("/user/signup")
    public TingTingResponse singup(@RequestBody UserSignUp userSignUp) {
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
