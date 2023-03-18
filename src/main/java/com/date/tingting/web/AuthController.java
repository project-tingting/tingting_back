package com.date.tingting.web;

import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.AuthService;
import com.date.tingting.web.requestDto.RegenerateTokenRequest;
import com.date.tingting.web.responseDto.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@Api(tags = "Auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final ResponseService responseService;

    @Autowired
    private final AuthService authService;


    @PostMapping("/regenerateToken")
    @ApiOperation(value="Access토큰 재발급", notes="Access토큰 재발급 API")
    @ApiImplicitParam(name = "regenerateTokenRequest", value = "Access, refresh 토큰")
    public TingTingResponse regenerateToken(@RequestBody  RegenerateTokenRequest regenerateTokenRequest) {
        return responseService.getTingTingResponse(authService.regenerateToken(regenerateTokenRequest));
     }

}
