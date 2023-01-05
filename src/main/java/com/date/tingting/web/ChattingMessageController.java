package com.date.tingting.web;

import com.date.tingting.domain.chattingMessage.ChattingMessage;
import com.date.tingting.domain.chattingMessage.ChattingMessageRepository;
import com.date.tingting.domain.user.UserRepository;
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
import java.util.List;


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

    @Autowired
    private final UserRepository userRepository;





    @PostMapping("/chat/{roomKey}")
    public TingTingResponse createChatMessage(@PathVariable String roomKey,
                                              @RequestBody ChattingMessageRequest chattingMessageRequest,
                                              @AuthenticationPrincipal User user) {
        chattingMessageService.addChattingMessage(roomKey, chattingMessageRequest, user);
        return responseService.getTingTingResponse("전송 완료");
    }




    @GetMapping("/chat/{roomKey}")
    public TingTingResponse findChatMessage(@PathVariable String roomKey) {

        List<ChattingMessage> chattingMessages = chattingMessageRepository.findAllByRoomKey(roomKey);

        List<com.date.tingting.domain.user.User> userList = userRepository.findAll();

        for(com.date.tingting.domain.user.User user : userList){
            for(ChattingMessage message : chattingMessages){
                if(message.getUuid().equals(user.getUuid())){
                    message.setUserId(user.getUserId());
                }
            }
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("messageList",chattingMessages);

        return responseService.getTingTingResponse(resultMap);

    }

}
