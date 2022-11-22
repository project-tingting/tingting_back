package com.date.tingting.domain.code;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code extends BaseTimeEntity {

    @Id
    private String code;
    @Column
    private String codeGroup;
    @Column
    private String isUse;
    @Column
    private String codeOrderNo;

    @Builder
    public Code(String code,
                String codeGroup,
                String isUse,
                String codeOrderNo) {
        this.code = code;
        this.codeGroup = codeGroup;
        this.isUse = isUse;
        this.codeOrderNo = codeOrderNo;
    }

}
