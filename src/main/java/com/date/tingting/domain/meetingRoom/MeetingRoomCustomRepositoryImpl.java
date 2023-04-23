package com.date.tingting.domain.meetingRoom;

import com.date.tingting.domain.meetingRoomUser.QMeetingRoomUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;


@Slf4j
@Service
public class MeetingRoomCustomRepositoryImpl implements MeetingRoomCustomRepository {

    JPAQueryFactory queryFactory;

    public MeetingRoomCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public String findMeetingRoom(String type, String currentTime, String gender) {

        QMeetingRoomUser meetingRoomUser = QMeetingRoomUser.meetingRoomUser;
        QMeetingRoom meetingRoom = QMeetingRoom.meetingRoom;

        Tuple tuple = queryFactory
                .select(meetingRoomUser.roomKey,
                        Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 'm' THEN 1 ELSE 0 END)", meetingRoomUser.gender),
                        Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 'w' THEN 1 ELSE 0 END)", meetingRoomUser.gender))
                .from(meetingRoomUser)
                .join(meetingRoom)
                .on(meetingRoom.roomKey.eq(meetingRoomUser.roomKey))
                .where(meetingRoom.type.eq(type))
                .where(meetingRoom.watingExpireDate.goe(currentTime))
                .where(meetingRoom.isFull.eq("0"))
                .where(meetingRoom.isStart.eq("0"))
                .groupBy(meetingRoomUser.roomKey)
                .having(Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = '" + gender + "' THEN 1 ELSE 0 END)", meetingRoomUser.gender).lt(Integer.parseInt(String.valueOf(type.charAt(0)))))
                .orderBy(QMeetingRoom.meetingRoom.registerDate.asc())
                .fetchFirst();

        String roomKey = null;

        if (tuple != null) {
            roomKey = tuple.get(meetingRoomUser.roomKey);
            Integer maleCount = tuple.get(Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 'm' THEN 1 ELSE 0 END)", meetingRoomUser.gender));
            Integer femaleCount = tuple.get(Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 'w' THEN 1 ELSE 0 END)", meetingRoomUser.gender));
         }
        // Q1
//        QMeetingRoom meetingRoom = QMeetingRoom.meetingRoom;
//        QMeetingRoomUser meetingRoomUser = QMeetingRoomUser.meetingRoomUser;
//
//        Expression<Integer> totalGenderSum = new CaseBuilder()
//                .when(meetingRoomUser.gender.eq("M")).then(1)
//                .otherwise(-1)
//                .sum();
//
//        List<Tuple> result = jpaQueryFactory.select(meetingRoom.roomKey, totalGenderSum)
//                .from(meetingRoom)
//                .leftJoin(meetingRoomUser).on(meetingRoom.roomKey.eq(meetingRoomUser.roomKey))
//                .groupBy(meetingRoom.roomKey)
//                .fetch();
//
//        for (Tuple tuple : result) {
//            String roomKey = tuple.get(meetingRoom.roomKey);
//            Integer genderSum = tuple.get(totalGenderSum);
//            System.out.println("Room Key: " + roomKey + ", Total Gender Sum: " + genderSum);
//        }

        //q2
//        QMeetingRoom meetingRoom = QMeetingRoom.meetingRoom;
//        QMeetingRoomUser meetingRoomUser = QMeetingRoomUser.meetingRoomUser;
//
//        List<Tuple> results = jpaQueryFactory.select(
//                        meetingRoom.roomKey,
//                        meetingRoomUser.gender,
//                        meetingRoomUser.gender.count())
//                .from(meetingRoom)
//                .join(meetingRoomUser)
//                .on(meetingRoom.roomKey.eq(meetingRoomUser.roomKey))
//                .groupBy(meetingRoom.roomKey, meetingRoomUser.gender)
//                .fetch();
//
//        for (Tuple result : results) {
//            String roomKey = result.get(meetingRoom.roomKey);
//            String gender = result.get(meetingRoomUser.gender);
//            Long count = result.get(meetingRoomUser.gender.count());
//            System.out.println("Room Key: " + roomKey + ", Gender: " + gender + ", Total Gender Count: " + count);
//        }

        //q3
//        QMeetingRoom meetingRoom = QMeetingRoom.meetingRoom;
//        QMeetingRoomUser meetingRoomUser = QMeetingRoomUser.meetingRoomUser;
//
//        List<Tuple> result = queryFactory
//                .select(meetingRoom.roomKey,
//                        meetingRoomUser.gender,
//                        meetingRoomUser.count())
//                .from(meetingRoom)
//                .innerJoin(meetingRoomUser)
//                .on(meetingRoom.roomKey.eq(meetingRoomUser.roomKey))
//                .groupBy(meetingRoom.roomKey, meetingRoomUser.gender)
//                .fetch();
//
//// 결과 출력
//        for (Tuple row : result) {
//            String roomKey = row.get(meetingRoom.roomKey);
//            String gender = row.get(meetingRoomUser.gender);
//            Long count = row.get(meetingRoomUser.count());
//
//            System.out.println("roomKey: " + roomKey + ", gender: " + gender + ", count: " + count);
//
//            // 결과 처리
//            // ...
//        }
//


        return roomKey;
    }


}
