package com.date.tingting.domain.userProfile;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.EntityManager;
import java.util.List;


@Slf4j
public class UserProfileCustomRepositoryImpl implements UserProfileCustomRepository {

    JPAQueryFactory jpaQueryFactory;

    public UserProfileCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

//    public List<UserProfile> xxxFunction(String uuid) {
//
//        List<Tuple> fetch =
//                jpaQueryFactory.select(uuid)
//                        .from(member)
//                        .join(member.team, team)
//                        .groupBy(team.name)
//                        .fetch();
//    }


}
