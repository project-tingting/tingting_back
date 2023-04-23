package com.date.tingting.domain.emailAuth;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class EmailAuthCustomRepositoryImpl implements EmailAuthCustomRepository{

    JPAQueryFactory jpaQueryFactory;

    public EmailAuthCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime currentTime) {

        EmailAuth emailAuth = jpaQueryFactory
                .selectFrom(QEmailAuth.emailAuth)
                .where(QEmailAuth.emailAuth.userEmail.eq(email),
                        QEmailAuth.emailAuth.authToken.eq(authToken),
                        QEmailAuth.emailAuth.expireDate.goe(currentTime),
                        QEmailAuth.emailAuth.expired.eq(false)
                        )
                .fetchFirst();

        return Optional.ofNullable(emailAuth);
    }
}
