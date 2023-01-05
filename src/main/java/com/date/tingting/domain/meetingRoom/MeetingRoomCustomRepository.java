package com.date.tingting.domain.meetingRoom;


import com.date.tingting.web.responseDto.MeetingRoomResponse;

public interface MeetingRoomCustomRepository {

    String findMeetingRoomKeyForMan(String type, String currentTime);
    String findMeetingRoomKeyForWoman(String type, String currentTime);


}
