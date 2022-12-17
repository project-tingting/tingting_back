package com.date.tingting.domain.meetingRoom;

import com.date.tingting.domain.meetingRoomUser.QMeetingRoomUser;
import com.date.tingting.web.responseDto.MeetingRoomResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;



@Slf4j
@Service
public class MeetingRoomCustomRepositoryImpl implements MeetingRoomCustomRepository {

    JPAQueryFactory jpaQueryFactory;

    public MeetingRoomCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    @Override
    public MeetingRoomResponse findMeetingRoomForMan(String type, String currentTime) {


//        MeetingRoom meetingRoom = jpaQueryFactory
//                .select(QMeetingRoom.meetingRoom)
//                .from(QMeetingRoom.meetingRoom)
//                .leftJoin(QMeetingRoomUser.meetingRoomUser)
//                .on(QMeetingRoom.meetingRoom.roomKey.eq(QMeetingRoomUser.meetingRoomUser.roomKey))
//                .where(QMeetingRoom.meetingRoom.type.eq(type),
//                        QMeetingRoom.meetingRoom.watingExpireDate.loe(currentTime)
//                        )
//                .groupBy(QMeetingRoom.meetingRoom.roomKey)
//                .orderBy(QMeetingRoom.meetingRoom.registerDate.asc())
//                .fetchFirst();


        Tuple meetingRoomInfo = (Tuple) jpaQueryFactory
                .select(QMeetingRoom.meetingRoom.roomKey, QMeetingRoom.meetingRoom.count())
                .from(QMeetingRoom.meetingRoom)
                .leftJoin(QMeetingRoomUser.meetingRoomUser)
                .on(QMeetingRoom.meetingRoom.roomKey.eq(QMeetingRoomUser.meetingRoomUser.roomKey))
                .where(QMeetingRoom.meetingRoom.type.eq(type),
                        QMeetingRoom.meetingRoom.watingExpireDate.loe(currentTime)
                )
                .groupBy(QMeetingRoom.meetingRoom.roomKey)
                .orderBy(QMeetingRoom.meetingRoom.registerDate.asc())
                .fetchFirst();

        if(meetingRoomInfo != null){
            MeetingRoomResponse meetingRoomResponse = new MeetingRoomResponse();
            meetingRoomResponse.setRoomKey(meetingRoomInfo.get(QMeetingRoom.meetingRoom.roomKey));
            //        meetingRoomResponse.setMeetingRoomCount(meetingRoomInfo.get(QMeetingRoom.meetingRoom.count()));
            return meetingRoomResponse;
        }else{
            return null;
        }
    }

    @Override
    public MeetingRoom findMeetingRoomForWoman(String type, String currentTime) {
        return null;
    }


}
