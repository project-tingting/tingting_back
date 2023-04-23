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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value="미팅 룸 유저 상태 변경", notes="매칭룸을 찾았을 때 거절, 수락 중 하나를 택할 경우의 상태 업데이트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomKey", value = "미팅룸 키"),
            @ApiImplicitParam(name = "status", value = "해당 미팅룸에 들어갈지에 대한 상태값"),
    })
    public TingTingResponse updateMeetingRoomUserStatus(@PathVariable String roomKey, @PathVariable String status, @AuthenticationPrincipal User user) {

        MeetingRoomUserRequest meetingRoomUserRequest = new MeetingRoomUserRequest();
        meetingRoomUserRequest.setRoomKey(roomKey);
        meetingRoomUserRequest.setStatus(status);
        meetingRoomUserRequest.setUuid(user.getUsername());
        meetingRoomUserService.updateMeetingRoomUserStatus(meetingRoomUserRequest);
        return responseService.getTingTingResponse("수락/거절 상태 업데이트 완료");
    }

    @GetMapping("/meetingroomuser")
    @ApiOperation(value="미팅 룸 정보 조회", notes="미팅 룸 정보 조회 API")
    public TingTingResponse getMeetingRoomByUuid(@AuthenticationPrincipal User user) {

        MeetingRoomUser meetingRoomUser = meetingRoomUserRepository.findByUuid(user.getUsername());

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("meetingRoomUser",meetingRoomUser);
        return responseService.getTingTingResponse(resultMap);
    }


}
