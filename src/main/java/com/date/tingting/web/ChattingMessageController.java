package com.date.tingting.web;

import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.web.requestDto.ChattingMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequiredArgsConstructor
public class ChattingMessageController {

    @Autowired
    private final ResponseService responseService;


//    @PostMapping("/chat/{roomKey}")
//    public TingTingResponse createChatMessage(@PathVariable String roomKey, @RequestBody ChattingMessageRequest chattingMessageRequest, @AuthenticationPrincipal User user) {
//
//    }
//
//
//    @GetMapping("/chat/{roomKey}")
//    public TingTingResponse findChatMessage() {
//
//    }

}
