package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.Inquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryMapper {
    void insertInquiry(Inquiry inquiry);
    void updateInquiry(@Param("inquiry") Inquiry inquiry);
    void deleteInquiry(@Param("inquiryId") int inquiryId, @Param("memberId") String memberId);
    Inquiry selectInquiry(int inquiryId);
    List<Inquiry> selectInquiriesByType(int typeId);
}

