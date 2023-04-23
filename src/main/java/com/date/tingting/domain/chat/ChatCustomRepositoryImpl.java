package com.date.tingting.domain.chat;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class ChatCustomRepositoryImpl implements ChatCustomRepository {
    JPAQueryFactory jpaQueryFactory;

    public ChatCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

}
