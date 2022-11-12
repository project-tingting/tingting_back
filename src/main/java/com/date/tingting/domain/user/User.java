package com.date.tingting.domain.user;

import lombok.*;

import javax.persistence.*;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String userEmail;

    @Column
    private Long userNo;

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
    @Column
    private String registerDate;
    @Column
    private String updateDate;


}
