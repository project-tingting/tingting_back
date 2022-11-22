package com.date.tingting.web;

import com.date.tingting.domain.code.Code;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.CodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CodeController {

    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final CodeService codeService;

    @GetMapping("/code/list/{codeGroup}")
    public TingTingResponse findAllByCodeGroup(@PathVariable String codeGroup) {

        List<Code> codeList = codeService.findAllByCodeGroup(codeGroup);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("codeList",codeList);
        return responseService.getTingTingResponse(resultMap);
    }

}
