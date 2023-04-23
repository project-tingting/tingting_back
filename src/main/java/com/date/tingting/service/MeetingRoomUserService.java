package com.date.tingting.service;

import com.date.tingting.domain.meetingRoomUser.MeetingRoomUser;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUserRepository;
import com.date.tingting.web.requestDto.MeetingRoomUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class MeetingRoomUserService {


    @Autowired
    private final MeetingRoomUserRepository meetingRoomUserRepository;


    @Transactional
    public void updateMeetingRoomUserStatus(MeetingRoomUserRequest meetingRoomUserRequest) {

        MeetingRoomUser meetingRoomUser =  meetingRoomUserRepository.findByRoomKeyAndUuid(meetingRoomUserRequest.getRoomKey(), meetingRoomUserRequest.getUuid());
        meetingRoomUser.updateMeetingRoomUserStatus(meetingRoomUserRequest.getStatus());
        //수락 거절중 거절하게 되면 룸에서 제외한다.
        if(meetingRoomUserRequest.getStatus().equals("-1")){
            meetingRoomUserRepository.deleteByUuid(meetingRoomUserRequest.getUuid());
        }
    }

}
