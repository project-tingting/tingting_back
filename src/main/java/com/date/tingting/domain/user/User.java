package com.date.tingting.domain.user;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;

@ToString
@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    private String userMail;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userNo;

    @Column
    private String password;
    @Column
    private String salt;
    @Column
    private String university;
    @Column
    private String major;
    @Column
    private String userName;
    @Column
    private String isDel;

//    @Column
//    @CreatedDate
//    private String registerDate;
//
//    @Column
//    private String updateDate;


    @Builder
    public User(String userMail, long userNo, String password, String salt, String university, String major, String userName, String isDel) {
        this.userMail = userMail;
        this.userNo = userNo;
        this.password = password;
        this.salt = salt;
        this.university = university;
        this.major = major;
        this.userName = userName;
        this.isDel = "0";
    }
}
