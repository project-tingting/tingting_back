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
    private String userId;
    @Column
    private String password;
    @Column
    private String userMail;
    @Column
    private String university;
    @Column
    private String major;
    @Column
    private String gender;
    @Column
    private String birthDay;
    @Column
    private String isDel;

    @Builder
    public User(String uuid,
                String userId,
                String password,
                String userMail,
                String university,
                String major,
                String gender,
                String birthDay,
                String isDel) {
        this.uuid = uuid;
        this.userMail = userMail;
        this.password = password;
        this.university = university;
        this.major = major;
        this.userId = userId;
        this.gender = gender;
        this.birthDay = birthDay;
        this.isDel = isDel;
    }
}
