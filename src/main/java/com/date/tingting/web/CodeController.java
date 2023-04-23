package com.date.tingting.web;

import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.CodeService;
import com.date.tingting.web.responseDto.CodeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Code")
@RequiredArgsConstructor
public class CodeController {

    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final CodeService codeService;

    @GetMapping("/code/list/{codeGroup}")
    @ApiOperation(value="코드 리스트 조회", notes="코드 리스트 조회 API")
    @ApiImplicitParam(name = "codeGroup", value = "코드 그룹")
    public TingTingResponse findAllByCodeGroup(@PathVariable String codeGroup) {

        List<CodeResponse> codeResponseList = codeService.findAllByCodeGroup(codeGroup);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("codeList",codeResponseList);
        return responseService.getTingTingResponse(resultMap);
    }

}
