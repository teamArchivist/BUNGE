package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.Inquiry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {
    void insertInquiry(Inquiry inquiry);

    void updateInquiry(Inquiry inquiry);

    void deleteInquiry(int id);

    Inquiry selectInquiryById(int id);

    List<Inquiry> selectInquiries(Integer typeId);
}
