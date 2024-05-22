package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.mapper.InquiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteInquiry(int inquiryId, String memberId) {
        inquiryMapper.deleteInquiry(inquiryId, memberId);
    }

    @Override
    public Inquiry getInquiryById(int inquiryId) {
        return inquiryMapper.selectInquiry(inquiryId);
    }

    @Override
    public List<Inquiry> getInquiriesByType(int typeId) {
        return inquiryMapper.selectInquiriesByType(typeId);
    }
}
