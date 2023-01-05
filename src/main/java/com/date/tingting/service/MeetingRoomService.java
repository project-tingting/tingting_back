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

        //방에 있는 남성, 여성 카운트 조회
        int manCount = meetingRoomUserRepository.countByRoomKeyAndGenderAndStatus(roomKey, "m", "10");
        int womanCount = meetingRoomUserRepository.countByRoomKeyAndGenderAndStatus(roomKey, "w", "10");

        MeetingRoomResponse meetingRoomResponse = new MeetingRoomResponse();
        meetingRoomResponse.setRoomKey(meetingRoom.getRoomKey());
        meetingRoomResponse.setType(meetingRoom.getType());
        meetingRoomResponse.setManCount(manCount);
        meetingRoomResponse.setWomanCount(womanCount);
        meetingRoomResponse.setWatingExpireDate(meetingRoom.getWatingExpireDate());

        return meetingRoomResponse;
    }


    @Transactional
    public String enterToMeetingRoom(MeetingRoomRequest meetingRoomRequest, User user) {


        com.date.tingting.domain.user.User userInfo  = userService.getUser(user.getUsername());

        if(userInfo == null){
            throw new TingTingCommonException("존재하지 않는 유저입니다.");
        }

        //todo
        //자신이 방에 이미 들어가 있는지 벨리데이션 체크 로직 추가

        //매칭을 구하는 만료 시간
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cal.add(Calendar.MINUTE, 0);
        String nowTime = sdformat.format(cal.getTime());

        //todo
        //방 매칭 순서
        //1) chattingStartDate가 5분이 지나지 않은 시점의 방중 isFull =0 인데 isStart = 1 인 방 가장 먼저 매칭 -> 채팅방 구현 후 다시 작업
        //2) 남녀 숫자를 확인 후, 남는 방 입장

        String roomKey = null;
        if(userInfo.getGender().equals("m")){
            roomKey = meetingRoomCustomRepositoryImpl.findMeetingRoomKeyForMan(meetingRoomRequest.getType(), nowTime);
        }else{
            roomKey = meetingRoomCustomRepositoryImpl.findMeetingRoomKeyForWoman(meetingRoomRequest.getType(), nowTime);
        }


        //없으면 방 최초 생성한 후 입장
        if(roomKey == null) {
            roomKey = GetRandomOut.getRandomStr(10);

            //웨이팅 만료 시간은 지금으로 부터 5분
            cal.add(Calendar.MINUTE, watingExpireDate);
            String watingExpireDate = sdformat.format(cal.getTime());

            MeetingRoom meetingRoom = MeetingRoom.builder()
                    .roomKey(roomKey)
                    .type(meetingRoomRequest.getType())
                    .isFull("0")
                    .isStart("0")
                    .watingExpireDate(watingExpireDate)
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
            //방을 찾을 경우
            MeetingRoomUser meetingRoomUser = MeetingRoomUser.builder()
                    .roomKey(roomKey)
                    .uuid(userInfo.getUuid())
                    .gender(userInfo.getGender())
                    .status("10")
                    .build();
            roomKey =  meetingRoomUserRepository.save(meetingRoomUser).getRoomKey();
        }

        return roomKey;
     }

}
