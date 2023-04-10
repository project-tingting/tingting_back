package com.date.tingting.web;

import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.MeetingRoomService;
import com.date.tingting.web.requestDto.MeetingRoomRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class MeetingRoomController {

    @Autowired
    private final MeetingRoomService meetingRoomService;
    @Autowired
    private final ResponseService responseService;


    @PostMapping("/meetingroom")
    @ApiOperation(value="매칭 시작", notes="매칭 시작 API")
    public TingTingResponse enterToMeetingRoom(@RequestBody MeetingRoomRequest meetingRoomRequest, @AuthenticationPrincipal User user) {

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("roomKey",meetingRoomService.enterToMeetingRoom(meetingRoomRequest, user));

        return responseService.getTingTingResponse(resultMap);
    }

    @GetMapping("/meetingroom/{roomKey}")
    @ApiOperation(value="미팅 룸 정보 조회", notes="roomKey를 기반으로 미팅룸 상태 확인")
    @ApiImplicitParam(name = "roomKey", value = "룸 고유키")
    public TingTingResponse getMeetingRoomInfo(@PathVariable String roomKey) {
        return responseService.getTingTingResponse(meetingRoomService.getMeetingRoomInfo(roomKey));
    }

}
