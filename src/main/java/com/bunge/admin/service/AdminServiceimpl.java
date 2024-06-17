package com.bunge.admin.service;

import com.bunge.admin.domain.Visitor;
import com.bunge.admin.mapper.AdminMapper;
import com.bunge.member.domain.Member;
import com.bunge.study.domain.StudyBoard;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceimpl implements AdminService {

    private AdminMapper adminMapper;

    @Autowired
    public AdminServiceimpl(AdminMapper adminMapper) {
        this.adminMapper=adminMapper;
    }

    @Override
    public void insertVisitor(String username) {
        Visitor visitor = adminMapper.findByUsername(username);
        if(visitor == null){
            visitor = new Visitor();
            visitor.setUserid(username);
            visitor.setVisitdate(new Date());
            adminMapper.insertVisitor(visitor);
        }else {
            visitor.setVisitdate(new Date());
            adminMapper.updateVisitor(visitor);
        }

    }

    @Override
    public int getVisitorCount() {
        return adminMapper.getVisitorCount();
    }

    @Override
    public int getjoinCount() {
        return adminMapper.getjoinCount();
    }

    @Override
    public int getstudyCount() {
        return adminMapper.getstudyCount();
    }

    @Override
    public int getreviewCount() {
        return adminMapper.getreviewCount();
    }

    @Override
    public List<Member> getmemberlist() {
        return adminMapper.getmemberlist();
    }

    @Override
    public List<StudyBoard> getstudylist() {
        return adminMapper.getstudylist();
    }

}
