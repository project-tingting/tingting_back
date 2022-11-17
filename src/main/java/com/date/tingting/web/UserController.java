package com.date.tingting.web;

import com.date.tingting.response.Response;
import com.date.tingting.response.ResponseService;
import com.date.tingting.service.UserService;
import com.date.tingting.web.responseDto.UserResponse;
import com.date.tingting.web.requestDto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final UserService userService;

    @GetMapping("/user/{uuid}")
    public Response findByUuid(@PathVariable String uuid) {
        return responseService.getResponse(userService.findByUuid(uuid));
     }

    @PostMapping("/user/register")
    public Response save(@RequestBody UserRequest userRequest){
        userRequest.validate();
        return responseService.getResponse(userService.save(userRequest));
    }


}
