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
    private String userEmail;
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
    @Column
    private String isActive;

    @Builder
    public User(String uuid,
                String userId,
                String password,
                String userEmail,
                String university,
                String major,
                String gender,
                String birthDay,
                String isDel,
                String isActive) {
        this.uuid = uuid;
        this.userEmail = userEmail;
        this.password = password;
        this.university = university;
        this.major = major;
        this.userId = userId;
        this.gender = gender;
        this.birthDay = birthDay;
        this.isDel = isDel;
        this.isActive = isActive;
    }

    public void emailVerifiedSuccess() {
        this.isActive = "1";
    }

}
