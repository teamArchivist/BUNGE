package com.bunge.inquiry.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.mapper.InquiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryMapper inquiryMapper;

    @Autowired
    private InquiryAttachmentService inquiryAttachmentService;

    @Transactional
    @Override
    public void addInquiry(Inquiry inquiry, List<MultipartFile> files) {
        inquiryMapper.insertInquiry(inquiry);
        inquiryAttachmentService.addAttachments(inquiry.getInquiryId(), files);
    }

    @Transactional
    @Override
    public void updateInquiry(Inquiry inquiry, List<MultipartFile> files) {
        inquiryMapper.updateInquiry(inquiry);
        inquiryAttachmentService.addAttachments(inquiry.getInquiryId(), files);
    }

    @Transactional
    @Override
    public void deleteInquiry(Long inquiryId) {
        inquiryAttachmentService.deleteAttachments(inquiryId);
        inquiryMapper.deleteInquiry(inquiryId);
    }

    @Override
    public Inquiry getInquiry(Long inquiryId) {
        return inquiryMapper.selectInquiry(inquiryId);
    }

    @Override
    public List<Inquiry> getAllInquiries(int limit, int offset) {
        return inquiryMapper.selectAllInquiries(limit, offset);
    }

    @Override
    public List<Inquiry> getInquiriesByType(int typeId, int limit, int offset) {
        return inquiryMapper.selectInquiriesByType(typeId, limit, offset);
    }

    @Override
    public int getInquiryCount() {
        return inquiryMapper.selectListCount();
    }
}


