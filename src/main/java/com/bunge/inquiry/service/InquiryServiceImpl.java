package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.mapper.InquiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryMapper inquiryMapper;

    @Override
    public void createInquiry(Inquiry inquiry) {
        inquiryMapper.insertInquiry(inquiry);
    }

    @Override
    public void updateInquiry(Inquiry inquiry) {
        inquiryMapper.updateInquiry(inquiry);
    }

    @Override
    public void deleteInquiry(int inquiryId) {
        inquiryMapper.deleteInquiry(inquiryId);
    }

    @Override
    public Inquiry getInquiryById(int inquiryId) {
        return inquiryMapper.selectInquiry(inquiryId);
    }

    @Override
    public List<Inquiry> getInquiriesByType(int typeId, int page, int limit) {
        int offset = (page - 1) * limit;
        return inquiryMapper.selectInquiriesByType(typeId, limit, offset);
    }

    @Override
    public List<Inquiry> getAllInquiries(int page, int limit) {
        int offset = (page - 1) * limit;
        return inquiryMapper.selectAllInquiries(limit, offset);
    }

    @Override
    public int getListCount() {
        return inquiryMapper.selectListCount();
    }
}
