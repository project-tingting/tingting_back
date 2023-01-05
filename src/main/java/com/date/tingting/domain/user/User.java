package com.date.tingting.domain.user;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@ToString
@Getter
@Entity
public class User extends BaseTimeEntity implements UserDetails {

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
    private String gender;
    @Column
    private String birthDay;
    @Column
    private String isDel;
    @Column
    private String isActive;
    @Column
    private String roles;

    public User() {
    }

    @Builder
    public User(String uuid, String userId, String password, String userEmail, String university, String gender, String birthDay, String isDel, String isActive, String roles) {
        this.uuid = uuid;
        this.userId = userId;
        this.password = password;
        this.userEmail = userEmail;
        this.university = university;
        this.gender = gender;
        this.birthDay = birthDay;
        this.isDel = isDel;
        this.isActive = isActive;
        this.roles = roles;
    }

    public void emailVerifiedSuccess() {
        this.isActive = "1";
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        getRoleList().forEach(r-> {
            authorities.add(()->r);
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
