package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.Inquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface InquiryMapper {
    void insertInquiry(Inquiry inquiry);
    void updateInquiry(@Param("inquiry") Inquiry inquiry);
    void deleteInquiry(@Param("inquiryId") int inquiryId);
    Inquiry selectInquiry(int inquiryId);
    List<Inquiry> selectInquiriesByType(@Param("typeId") int typeId, @Param("limit") int limit, @Param("offset") int offset);
    List<Inquiry> selectAllInquiries(@Param("limit") int limit, @Param("offset") int offset);
    int selectListCount();
}

