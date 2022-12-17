package com.date.tingting.service;

import com.date.tingting.domain.meetingRoom.MeetingRoom;
import com.date.tingting.domain.meetingRoom.MeetingRoomCustomRepositoryImpl;
import com.date.tingting.domain.meetingRoom.MeetingRoomRepository;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUser;
import com.date.tingting.domain.meetingRoomUser.MeetingRoomUserRepository;
import com.date.tingting.handler.exception.TingTingCommonException;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.utils.GetRandomOut;
import com.date.tingting.web.requestDto.MeetingRoomRequest;
import com.date.tingting.web.responseDto.MeetingRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Transactional
@RequiredArgsConstructor
@Service
public class MeetingRoomService {
    @Autowired
    private final MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private final MeetingRoomCustomRepositoryImpl meetingRoomCustomRepositoryImpl;

    @Autowired
    private final MeetingRoomUserRepository meetingRoomUserRepository;
    @Autowired
    private final UserService userService;

    private final int watingExpireDate = 5;


    public MeetingRoomResponse getMeetingRoomInfo(String roomKey) {

        MeetingRoom meetingRoom = meetingRoomRepository.findByRoomKey(roomKey);

        if(meetingRoom == null){
            throw new TingTingDataNotFoundException();
        }

        MeetingRoomResponse meetingRoomResponse = new MeetingRoomResponse();
        meetingRoomResponse.setRoomKey(meetingRoom.getRoomKey());
        meetingRoomResponse.setRoomReadyStatus("1");
        meetingRoomResponse.setWatingExpireDate(meetingRoom.getWatingExpireDate());

        return meetingRoomResponse;
    }


    @Transactional
    public String enterToMeetingRoom(MeetingRoomRequest meetingRoomRequest, User user) {


        com.date.tingting.domain.user.User userInfo  = userService.getUser(user.getUsername());

        if(userInfo == null){
            throw new TingTingCommonException("존재하지 않는 유저입니다.");
        }


        //매칭을 구하는 만료 시간
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cal.add(Calendar.MINUTE, watingExpireDate);
        String today = sdformat.format(cal.getTime());

        //유저가 들어갈 수 있는 방 검색
        MeetingRoomResponse meetingRoomResponse =  meetingRoomCustomRepositoryImpl.findMeetingRoomForMan(meetingRoomRequest.getType(), today);


        String roomKey = null;

        //없으면 방 최초 생성한 후 입장
        if(meetingRoomResponse == null) {
            roomKey = GetRandomOut.getRandomStr(10);
            int manCount = 0;
            int womanCount = 0;

            if (userInfo.getGender().equals("m")) {
                manCount++;
            } else {
                womanCount++;
            }


            MeetingRoom meetingRoom = MeetingRoom.builder()
                    .roomKey(roomKey)
                    .type(meetingRoomRequest.getType())
                    .manCount(manCount)
                    .womanCount(womanCount)
                    .watingExpireDate(today)
                    .build();

            roomKey =  meetingRoomRepository.save(meetingRoom).getRoomKey();

            MeetingRoomUser meetingRoomUser = MeetingRoomUser.builder()
                    .roomKey(roomKey)
                    .uuid(userInfo.getUuid())
                    .gender(userInfo.getGender())
                    .status("10")
                    .build();

            roomKey =  meetingRoomUserRepository.save(meetingRoomUser).getRoomKey();
        }else{
            return "ee";
        }

        return roomKey;
     }

}
