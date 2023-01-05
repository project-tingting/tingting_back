package com.date.tingting.web;

import com.date.tingting.domain.meetingRoom.MeetingRoomRepository;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUser;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUserRepository;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.MeetingRoomService;
import com.date.tingting.service.MeetingRoomUserService;
import com.date.tingting.web.requestDto.MeetingRoomRequest;
import com.date.tingting.web.requestDto.MeetingRoomUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class MeetingRoomUserController {

    @Autowired
    private final MeetingRoomUserService meetingRoomUserService;

    @Autowired
    private final ResponseService responseService;

    @Autowired
    private final MeetingRoomUserRepository meetingRoomUserRepository;


    @PutMapping("/meetingroomuser/{roomKey}/{status}")
    public TingTingResponse updateMeetingRoomUserStatus(@PathVariable String roomKey, @PathVariable String status, @AuthenticationPrincipal User user) {

        MeetingRoomUserRequest meetingRoomUserRequest = new MeetingRoomUserRequest();
        meetingRoomUserRequest.setRoomKey(roomKey);
        meetingRoomUserRequest.setStatus(status);
        meetingRoomUserRequest.setUuid(user.getUsername());
        meetingRoomUserService.updateMeetingRoomUserStatus(meetingRoomUserRequest);
        return responseService.getTingTingResponse("수락/거절 상태 업데이트 완료");
    }

    @GetMapping("/meetingroomuser")
    public TingTingResponse getMeetingRoomByUuid(@AuthenticationPrincipal User user) {

        MeetingRoomUser meetingRoomUser = meetingRoomUserRepository.findByUuid(user.getUsername());

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("meetingRoomUser",meetingRoomUser);
        return responseService.getTingTingResponse(resultMap);
    }


}
