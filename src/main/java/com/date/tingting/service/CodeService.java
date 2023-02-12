package com.date.tingting.service;

import com.date.tingting.domain.code.Code;
import com.date.tingting.domain.code.CodeRepository;
import com.date.tingting.domain.partyGroup.PartyGroup;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.web.responseDto.CodeResponse;
import com.date.tingting.web.responseDto.PartyUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeService {
    @Autowired
    private final CodeRepository codeRepository;

    @Transactional(readOnly = true)
    public List<CodeResponse> findAllByCodeGroup(String codeGroup) {
        List<Code> codeList = codeRepository.findAllByCodeGroup(codeGroup);

        if(codeList == null || codeList.size() < 1){
            throw new TingTingDataNotFoundException();
        }

        List<CodeResponse> codeResponseList = new ArrayList<>();

        codeList.forEach(u -> {
            CodeResponse codeResponse = CodeResponse.builder().
                    code(u.getCode()).
                    codeName(u.getCodeName()).
                    codeGroup(u.getCodeGroup()).
                    description(u.getDescription()).
                    isUse(u.getIsUse()).
                    codeOrderNo(u.getCodeOrderNo()).
                    build();

            codeResponseList.add(codeResponse);
        });

        return codeResponseList;
    }

}
