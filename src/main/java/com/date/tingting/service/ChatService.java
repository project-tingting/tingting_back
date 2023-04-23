package com.date.tingting.service;

import com.date.tingting.domain.chat.Chat;
import com.date.tingting.domain.chat.ChatRepository;
import com.date.tingting.domain.meetingRoom.MeetingRoom;
import com.date.tingting.domain.meetingRoom.MeetingRoomRepository;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUser;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUserRepository;
import com.date.tingting.handler.exception.TingTingCommonException;
import com.date.tingting.web.requestDto.ChatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private final ChatRepository chatRepository;
    @Autowired
    private final MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private final MeetingRoomUserRepository meetingRoomUserRepository;

    public void createChat(ChatRequest chatRequest) {

        MeetingRoom meetingRoom = meetingRoomRepository.findByRoomKey(chatRequest.getRoomKey());
        if(meetingRoom == null){
            throw new TingTingCommonException("미팅룸이 존재하지 않습니다");
        }

        MeetingRoomUser meetingRoomUser = meetingRoomUserRepository.findByRoomKeyAndUuid(meetingRoom.getRoomKey(), chatRequest.getUuid());
        if(meetingRoomUser == null) {
            throw new TingTingCommonException("미팅룸에 존재하는 유저가 아닙니다");
        }

        Chat chat = Chat.builder()
                .roomKey(meetingRoom.getRoomKey())
                .uuid(chatRequest.getUuid())
                .message(chatRequest.getMessage())
                .build();

        chatRepository.save(chat);
    }
}
