package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.InquiryAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryAttachmentMapper {
    void insertAttachment(InquiryAttachment attachment);
    List<InquiryAttachment> selectAttachmentsByInquiryId(int inquiryId);
    void deleteAttachmentsByInquiryId(int inquiryId);
}