package com.date.tingting.domain.meetingRoomUser;


import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomUserRepository extends JpaRepository<MeetingRoomUser, String> {

    MeetingRoomUser findByRoomKeyAndUuid(String roomkey, String uuid);

    MeetingRoomUser findByUuid(String uuid);


    int countByRoomKeyAndGenderAndStatus(String roomkey, String gender, String status);
    void deleteByUuid(String uuid);


}
