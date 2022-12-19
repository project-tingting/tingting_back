package com.date.tingting.service;

import com.date.tingting.domain.chattingMessage.ChattingMessage;
import com.date.tingting.domain.chattingMessage.ChattingMessageRepository;
import com.date.tingting.domain.meetingRoom.MeetingRoom;
import com.date.tingting.domain.meetingRoom.MeetingRoomRepository;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUser;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUserRepository;
import com.date.tingting.handler.exception.TingTingCommonException;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.web.requestDto.ChattingMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChattingMessageService {
    @Autowired
    private final ChattingMessageRepository chattingMessageRepository;
    @Autowired
    private final MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private final MeetingRoomUserRepository meetingRoomUserRepository;
    @Autowired
    private final UserService userService;


    public void addChattingMessage(String roomKey, ChattingMessageRequest chattingMessageRequest, User user) {
        com.date.tingting.domain.user.User userInfo  = userService.getUser(user.getUsername());

        if(userInfo == null){
            throw new TingTingCommonException("존재하지 않는 유저입니다.");
        }

        MeetingRoom meetingRoom = meetingRoomRepository.findByRoomKey(roomKey);

        if(meetingRoom == null){
            throw new TingTingDataNotFoundException();
        }

        MeetingRoomUser meetingRoomUser = meetingRoomUserRepository.findByRoomKeyAndUuid(meetingRoom.getRoomKey(), user.getUsername());
        if(meetingRoomUser == null) {
            throw new TingTingCommonException("해당 미팅룸에 접근할 수 없습니다.");
        }

        ChattingMessage message = ChattingMessage.builder()
                .roomKey(meetingRoom.getRoomKey())
                .uuid(user.getUsername())
                .message(chattingMessageRequest.getMessage())
                .build();

        chattingMessageRepository.save(message);
    }
}
