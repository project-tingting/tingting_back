package com.date.tingting.web;

import com.date.tingting.domain.user.User;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.response.ResponseService;
import com.date.tingting.service.UserService;
import com.date.tingting.web.requestDto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TingTingResponse findByUuid(@PathVariable String uuid) {

        User user = userService.findByUuid(uuid);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",user);
        return responseService.getTingTingResponse(resultMap);
    }

    @GetMapping("/user/list")
    public TingTingResponse findAll() {

        List<User> userList = userService.findAll();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userList",userList);
        return responseService.getTingTingResponse(resultMap);
    }

    @PostMapping("/user/register")
    public TingTingResponse save(@RequestBody UserRequest userRequest){

        userRequest.validate();
        String uuid = userService.save(userRequest);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("uuid",uuid);
        return responseService.getTingTingResponse(resultMap);
    }

}
