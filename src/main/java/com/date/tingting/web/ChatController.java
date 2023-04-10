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


    @PostMapping("/chat/{roomKey}")
    public TingTingResponse createChat(@PathVariable String roomKey, @RequestBody ChatRequest chatRequest, @AuthenticationPrincipal User user) {
        chatService.addChat(roomKey, chatRequest, user);
        return responseService.getTingTingResponse("전송 완료");
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
