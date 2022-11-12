package com.date.tingting.domain.user;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
    public User(String userMail, String password, String university, String major, String userName ) {
        this.userMail = userMail;
        this.password = password;
        this.university = university;
        this.major = major;
        this.userName = userName;
    }

}
