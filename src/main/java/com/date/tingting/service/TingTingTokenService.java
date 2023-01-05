package com.date.tingting.service;

import com.date.tingting.domain.tingTingToken.TingTingToken;
import com.date.tingting.domain.tingTingToken.TingTingTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TingTingTokenService {

    @Autowired
    private final TingTingTokenRepository tingtingTokenRepository;


    public TingTingToken getTingTingTokenInfo(String uuid) {

        TingTingToken tingTingToken = tingtingTokenRepository.findByUuid(uuid);

        return tingTingToken;
    }




}
