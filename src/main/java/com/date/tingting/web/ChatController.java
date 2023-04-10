package com.date.tingting.web;

import com.date.tingting.domain.chat.Chat;
import com.date.tingting.domain.chat.ChatRepository;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.ChatService;
import com.date.tingting.web.requestDto.ChatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final ChatService chatService;
    @Autowired
    private final ChatRepository chatRepository;
    @Autowired
    private final UserRepository userRepository;

    @MessageMapping("/{roomKey}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{roomKey}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public TingTingResponse createChat(@DestinationVariable String roomKey, ChatRequest chatRequest, @AuthenticationPrincipal User user) {

        chatRequest.setUuid(user.getUsername());
        chatRequest.setRoomKey(roomKey);
        chatService.createChat(chatRequest);
        return responseService.getTingTingResponse("create chat successfully");
    }


    @GetMapping("/chat/{roomKey}")
    public TingTingResponse findChat(@PathVariable String roomKey) {

        List<Chat> chatList = chatRepository.findAllByRoomKey(roomKey);

        List<com.date.tingting.domain.user.User> userList = userRepository.findAll();

        for(com.date.tingting.domain.user.User user : userList){
            for(Chat chat : chatList){
                if(chat.getUuid().equals(user.getUuid())){
                    chat.setUserId(user.getUserId());
                }
            }
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("chatList", chatList);

        return responseService.getTingTingResponse(resultMap);
    }

}
