package com.date.tingting.domain.chattingMessage;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

public class ChattingMessageCustomRepositoryImpl implements ChattingMessageCustomRepository{
    JPAQueryFactory jpaQueryFactory;

    public ChattingMessageCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

}
