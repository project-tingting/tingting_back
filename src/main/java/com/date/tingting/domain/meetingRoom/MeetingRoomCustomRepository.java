package com.date.tingting.domain.meetingRoom;


public interface MeetingRoomCustomRepository {
    String findMeetingRoom(String type, String currentTime, String gender);


}
