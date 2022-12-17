package com.date.tingting.domain.meetingRoom;


import com.date.tingting.web.responseDto.MeetingRoomResponse;

public interface MeetingRoomCustomRepository {

    MeetingRoomResponse findMeetingRoomForMan(String type, String currentTime);
    MeetingRoom findMeetingRoomForWoman(String type, String currentTime);


}
