package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.InquiryAttachment;
import com.bunge.inquiry.mapper.InquiryAttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryAttachmentServiceImpl implements InquiryAttachmentService {

    @Autowired
    private InquiryAttachmentMapper inquiryAttachmentMapper;

    @Override
    public void addAttachment(InquiryAttachment attachment) {
        inquiryAttachmentMapper.insertAttachment(attachment);
    }

    @Override
    public List<InquiryAttachment> getAttachmentsByInquiryId(int inquiryId) {
        return inquiryAttachmentMapper.selectAttachmentsByInquiryId(inquiryId);
    }

    @Override
    public void deleteAttachmentsByInquiryId(int inquiryId) {
        inquiryAttachmentMapper.deleteAttachmentsByInquiryId(inquiryId);
    }
}
