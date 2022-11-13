package com.date.tingting.web;

import com.date.tingting.service.UserService;
import com.date.tingting.web.dto.UserDto;
import com.date.tingting.web.dto.UserRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("/user/{userMail}")
    public UserDto findByUserMail(@PathVariable String userMail) {
        return userService.findByUserMail(userMail);
    }

    @PostMapping("/user")
    public long save(@RequestBody UserRegister userRegister) {
        userRegister.validate();
        return userService.save(userRegister);
    }


}
