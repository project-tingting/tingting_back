package com.date.tingting.web;

import com.date.tingting.domain.chattingMessage.ChattingMessageRepository;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.ChattingMessageService;
import com.date.tingting.web.requestDto.ChattingMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ChattingMessageController {

    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final ChattingMessageService chattingMessageService;

    @Autowired
    private final ChattingMessageRepository chattingMessageRepository;


    @PostMapping("/chat/{roomKey}")
    public TingTingResponse createChatMessage(@PathVariable String roomKey,
                                              @RequestBody ChattingMessageRequest chattingMessageRequest,
                                              @AuthenticationPrincipal User user) {
        chattingMessageService.addChattingMessage(roomKey, chattingMessageRequest, user);
        return responseService.getTingTingResponse("전송 완료");
    }




    @GetMapping("/chat/{roomKey}")
    public TingTingResponse findChatMessage(@PathVariable String roomKey) {

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("messageList",chattingMessageRepository.findAllByRoomKey(roomKey));

        return responseService.getTingTingResponse(resultMap);

    }

}
