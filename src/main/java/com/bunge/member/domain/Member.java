package com.bunge.member.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class Member implements UserDetails {
    private String id;
    private String pwd;
    private String name;
    private String nick;
    private String gender; //성별
    private String zipcode;
    private String addr1;
    private String addr2;
    private String phone;
    private String email;
    private Date   birthdate;
    private String profile; //첨부될 파일의 이름
    private String role;
    private int readpoint;
    private int cnt;
    private String profile_original; //첨부될 파일의 이름

    private MultipartFile uploadfile;

    private int reportCount;

    private int   suspended; // 정지 여부를 나타내는 필드
    private LocalDate suspensionEndDate; // 정지 종료 일자를 나타내는 필드

    private int failedAttempts;     //로그인 실패 횟수를 나타내는 필드
    private boolean accuntNonLocked;//계정 장금 상태를 나타내는 필드

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(this.getRole()));
        return role;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {
        if(this.suspended == 1) {
            return false;
        }
        return true;
    }
}
