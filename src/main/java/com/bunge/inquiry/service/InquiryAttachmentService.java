package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.InquiryAttachment;

import java.util.List;

public interface InquiryAttachmentService {
    void addAttachment(InquiryAttachment attachment);
    List<InquiryAttachment> getAttachmentsByInquiryId(int inquiryId);
    void deleteAttachmentsByInquiryId(int inquiryId);
}
