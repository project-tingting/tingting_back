package com.date.tingting.domain.user;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;


@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    private String uuid;
    @Column
    private String userMail;
    @Column
    private String password;
    @Column
    private String university;
    @Column
    private String major;
    @Column
    private String nickName;
    @Column
    private String gender;
    @Column
    private String birthDate;
    @Column
    private String isDel;

    @Builder
    public User(String uuid,
                String userMail,
                String password,
                String university,
                String major,
                String nickName,
                String gender,
                String birthDate,
                String isDel) {
        this.uuid = uuid;
        this.userMail = userMail;
        this.password = password;
        this.university = university;
        this.major = major;
        this.nickName = nickName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.isDel = isDel;
    }
}
