package com.bunge.member.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class Member {
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
    private Date   joindate;
    private Date   birthdate;
    private String role;

}
