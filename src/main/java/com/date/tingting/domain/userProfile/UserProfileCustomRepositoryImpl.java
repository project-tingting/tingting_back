package com.date.tingting.domain.userProfile;

import com.date.tingting.web.requestDto.UserProfileRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import static com.date.tingting.domain.userProfile.QUserProfile.userProfile;


@Slf4j
@Service
public class UserProfileCustomRepositoryImpl implements UserProfileCustomRepository {

    JPAQueryFactory jpaQueryFactory;

    public UserProfileCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    public long deleteUserProfile(String uuid, String topic) {

        long count = jpaQueryFactory
                .delete(userProfile)
                .where(userProfile.uuid.eq(uuid))
                .where(userProfile.topic.eq(topic))
                .execute();

        return count;
    }



}
