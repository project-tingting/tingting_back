package com.date.tingting.web.responseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CodeResponse {

    @ApiModelProperty(example = "코드")
    private String code;
    @ApiModelProperty(example = "코드 이름")
    private String codeName;
    @ApiModelProperty(example = "코드 그룹")
    private String codeGroup;
    @ApiModelProperty(example = "코드 설명")
    private String description;
    @ApiModelProperty(example = "코드 사용 유무")
    private String isUse;
    @ApiModelProperty(example = "코드 정렬 순서")
    private String codeOrderNo;
}
