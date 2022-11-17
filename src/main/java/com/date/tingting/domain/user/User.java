package com.date.tingting.domain.user;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@ToString
@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    private String uuid;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userNo;
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

    @Column
    private LocalDateTime registerDate;

    @Column
    private LocalDateTime updateDate;

    @Builder
    public User(String uuid, String userMail, long userNo, String password, String university, String major, String nickName, String gender, String birthDate, LocalDateTime registerDate, LocalDateTime updateDate, String isDel) {
        this.uuid = uuid;
        this.userMail = userMail;
        this.userNo = userNo;
        this.password = password;
        this.university = university;
        this.major = major;
        this.nickName = nickName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.registerDate = registerDate;
        this.updateDate = updateDate;
        this.isDel = "0";
    }
}
