package com.date.tingting.domain.chattingMessage;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingMessageRepository extends JpaRepository<ChattingMessage, String> {


    List<ChattingMessage> findAllByRoomKey(String roomKey);


}
