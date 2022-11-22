package com.date.tingting.service;

import com.date.tingting.domain.code.Code;
import com.date.tingting.domain.code.CodeRepository;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    @Autowired
    private final CodeRepository codeRepository;

    @Transactional(readOnly = true)
    public List<Code> findAllByCodeGroup(String codeGroup) {
        List<Code> codeList = codeRepository.findAllByCodeGroup(codeGroup);

        if(codeList == null || codeList.size() < 1){
            throw new TingTingDataNotFoundException();
        }

        return codeList;
    }

}
