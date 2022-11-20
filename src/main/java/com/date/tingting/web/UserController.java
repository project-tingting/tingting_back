package com.date.tingting.web;

import com.date.tingting.response.ListResponse;
import com.date.tingting.response.SingleResponse;
import com.date.tingting.response.ResponseService;
import com.date.tingting.service.UserService;
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
    public SingleResponse findByUuid(@PathVariable String uuid) {
        return responseService.getSingleResponse(userService.findByUuid(uuid));
    }

    @GetMapping("/user/list")
    public ListResponse findAll() {
        return responseService.getListResponse(userService.findAll());
    }

    @PostMapping("/user/register")
    public SingleResponse save(@RequestBody UserRequest userRequest){
        userRequest.validate();
        return responseService.getSingleResponse(userService.save(userRequest));
    }

}
