package com.date.tingting.web;

import com.date.tingting.service.UserService;
import com.date.tingting.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userMail}")
    public UserDto findByUserMail(@PathVariable String userMail) {
        return userService.findByUserMail(userMail);
    }

    @PostMapping("/user")
    public long save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }


}
