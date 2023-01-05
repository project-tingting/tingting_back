package com.date.tingting.web;

import com.date.tingting.domain.meetingRoom.MeetingRoom;
import com.date.tingting.domain.meetingRoom.MeetingRoomRepository;
import com.date.tingting.domain.tingTingToken.TingTingToken;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.MeetingRoomService;
import com.date.tingting.service.PartyGroupService;
import com.date.tingting.web.requestDto.MeetingRoomRequest;
import com.date.tingting.web.responseDto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MeetingRoomController {
    @Autowired
    private final MeetingRoomService meetingRoomService;
    @Autowired
    private final ResponseService responseService;


    @PostMapping("/meetingroom")
    public TingTingResponse enterToMeetingRoom(@RequestBody MeetingRoomRequest meetingRoomRequest, @AuthenticationPrincipal User user) {

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("roomKey",meetingRoomService.enterToMeetingRoom(meetingRoomRequest, user));

        return responseService.getTingTingResponse(resultMap);
    }


    //roomKey를 기반으로 미팅룸 상태 확인
    @GetMapping("/meetingroom/{roomKey}")
    public TingTingResponse getMeetingRoomInfo(@PathVariable String roomKey) {
        return responseService.getTingTingResponse(meetingRoomService.getMeetingRoomInfo(roomKey));
    }

}
