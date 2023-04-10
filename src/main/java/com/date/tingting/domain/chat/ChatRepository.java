package com.date.tingting.domain.chat;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {
    List<Chat> findAllByRoomKey(String roomKey);
}
