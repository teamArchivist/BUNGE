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


    @Transactional
    @Override
    public void addInquiry(Inquiry inquiry) {
        inquiryMapper.insertInquiry(inquiry);
    }

    @Transactional
    @Override
    public void updateInquiry(Inquiry inquiry) {
        inquiryMapper.updateInquiry(inquiry);
    }

    @Transactional
    @Override
    public void deleteInquiry(Long inquiryId) {
        inquiryMapper.deleteInquiry(inquiryId);
    }

    @Override
    public Inquiry getInquiryById(Long inquiryId) {
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

    public Inquiry getView(Long inquiryId) {
        return inquiryMapper.getView(inquiryId);
    }
}


