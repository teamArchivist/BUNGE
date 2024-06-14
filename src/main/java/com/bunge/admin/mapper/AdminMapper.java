package com.bunge.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.bunge.admin.domain.Visitor;

@Mapper
public interface AdminMapper {

    Visitor findByUsername(String username);

    void insertVisitor(Visitor visitor);

    void updateVisitor(Visitor visitor);

    int getVisitorCount();

    int getjoinCount();

    int getstudyCount();

    int getreviewCount();
}
