package com.date.tingting.domain.meetingRoom;


import com.date.tingting.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, String> {

    MeetingRoom findByRoomKey(String roomKey);

}
