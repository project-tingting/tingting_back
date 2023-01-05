package com.date.tingting.domain.tingTingToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TingTingTokenRepository extends JpaRepository<TingTingToken, String> {

    TingTingToken findByUuid(String uuid);
}
