package com.date.tingting.web;

import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.service.UserService;
import com.date.tingting.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String test() {
        return "hello world";
    }

    @PostMapping("/user")
    public Long save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }






}
