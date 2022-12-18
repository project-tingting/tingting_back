package com.date.tingting.domain.meetingRoomUser;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRoomUserRepository extends JpaRepository<MeetingRoomUser, String> {

    MeetingRoomUser findByRoomKeyAndUuid(String roomkey, String uuid);

    int countByRoomKeyAndGenderAndStatus(String roomkey, String gender, String status);

}
