package com.bunge.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
public class memberController {
    private  static  final Logger logger = LoggerFactory.getLogger((memberController.class));

    private MemberService memberservice;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public memberController(MemberService memberservice, PasswordEncoder passwordEncoder) {
        this.memberservice=memberservice;
        this.passwordEncoder=passwordEncoder;

    }

}
