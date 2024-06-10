package com.bunge.inquiry.mapper;

import java.util.List;

import com.bunge.inquiry.domain.Inquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InquiryMapper {
    int selectListCount();
    List<Inquiry> selectInquiriesByType(@Param("typeId") int typeId, @Param("limit") int limit, @Param("offset") int offset);
    List<Inquiry> selectAllInquiries(@Param("limit") int limit, @Param("offset") int offset);
    Inquiry selectInquiry(@Param("inquiryId") Long inquiryId);
    void insertInquiry(Inquiry inquiry);
    int updateInquiry(Inquiry inquiry);
    void deleteInquiry(@Param("inquiryId") Long inquiryId);
    Inquiry getView(Long inquiryId);
}


