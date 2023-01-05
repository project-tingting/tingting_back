package com.date.tingting.domain.meetingRoom;

import com.date.tingting.domain.meetingRoomUser.QMeetingRoomUser;
import com.date.tingting.web.responseDto.MeetingRoomResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.util.List;


@Slf4j
@Service
public class MeetingRoomCustomRepositoryImpl implements MeetingRoomCustomRepository {

    JPAQueryFactory jpaQueryFactory;

    public MeetingRoomCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    @Override
    public String findMeetingRoomKeyForMan(String type, String currentTime) {

        BooleanBuilder builder = new BooleanBuilder();

        QMeetingRoom meetingRoom = QMeetingRoom.meetingRoom;
        QMeetingRoomUser meetingRoomUser = QMeetingRoomUser.meetingRoomUser;

        //todo
        // 실무에서는 튜플을 사용하지않고 DTO를 주로 사용한다
        Tuple firstTuple = jpaQueryFactory.select(meetingRoom.roomKey, meetingRoom.count())
                .from(meetingRoom)
                .leftJoin(QMeetingRoomUser.meetingRoomUser)
                .on(QMeetingRoom.meetingRoom.roomKey.eq(QMeetingRoomUser.meetingRoomUser.roomKey))
                .where( builder.and(meetingRoomUser.gender.eq("m")))
                .where(QMeetingRoom.meetingRoom.type.eq(type))
                .where(QMeetingRoom.meetingRoom.watingExpireDate.goe(currentTime))
                .where(QMeetingRoom.meetingRoom.isFull.eq("0"))
                .where(QMeetingRoom.meetingRoom.isStart.eq("0"))
                .groupBy(meetingRoom.roomKey)
                .having(meetingRoom.count().lt(Integer.parseInt(String.valueOf(type.charAt(0)))))
                .orderBy(QMeetingRoom.meetingRoom.registerDate.asc())
                .fetchFirst();


        //todo
        // 실무에서는 튜플을 사용하지않고 DTO를 주로 사용한다
        Tuple tuple = jpaQueryFactory.select(meetingRoom.roomKey, meetingRoom.count())
                .from(meetingRoom)
                .leftJoin(QMeetingRoomUser.meetingRoomUser)
                .on(QMeetingRoom.meetingRoom.roomKey.eq(QMeetingRoomUser.meetingRoomUser.roomKey))
                .where( builder.and(meetingRoomUser.gender.eq("m")))
                .where(QMeetingRoom.meetingRoom.type.eq(type))
                .where(QMeetingRoom.meetingRoom.watingExpireDate.goe(currentTime))
                .where(QMeetingRoom.meetingRoom.isFull.eq("0"))
                .where(QMeetingRoom.meetingRoom.isStart.eq("0"))
                .groupBy(meetingRoom.roomKey)
                .having(meetingRoom.count().lt(Integer.parseInt(String.valueOf(type.charAt(0)))))
                .orderBy(QMeetingRoom.meetingRoom.registerDate.asc())
                .fetchFirst();


        if(tuple != null){
            return tuple.get(meetingRoom.roomKey);
        }else{
            return null;
        }
    }

    @Override
    public String findMeetingRoomKeyForWoman(String type, String currentTime) {

        BooleanBuilder builder = new BooleanBuilder();

        QMeetingRoom meetingRoom = QMeetingRoom.meetingRoom;
        QMeetingRoomUser meetingRoomUser = QMeetingRoomUser.meetingRoomUser;

        //todo
        // 실무에서는 튜플을 사용하지않고 DTO를 주로 사용한다
        Tuple tuple = jpaQueryFactory.select(meetingRoom.roomKey, meetingRoom.count())
                .from(meetingRoom)
                .leftJoin(QMeetingRoomUser.meetingRoomUser)
                .on(QMeetingRoom.meetingRoom.roomKey.eq(QMeetingRoomUser.meetingRoomUser.roomKey))
                .where( builder.and(meetingRoomUser.gender.eq("w")))
                .where(QMeetingRoom.meetingRoom.type.eq(type))
                .where(QMeetingRoom.meetingRoom.watingExpireDate.goe(currentTime))
                .where(QMeetingRoom.meetingRoom.isFull.eq("0"))
                .where(QMeetingRoom.meetingRoom.isStart.eq("0"))
                .groupBy(meetingRoom.roomKey)
                .having(meetingRoom.count().lt(Integer.parseInt(String.valueOf(type.charAt(0)))))
                .orderBy(QMeetingRoom.meetingRoom.registerDate.asc())
                .fetchFirst();


        if(tuple != null){
            return tuple.get(meetingRoom.roomKey);
        }else{
            return null;
        }
    }


}
