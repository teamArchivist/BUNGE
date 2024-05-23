package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.Inquiry;

import java.util.List;

public interface InquiryService {
    void createInquiry(Inquiry inquiry);
    void updateInquiry(Inquiry inquiry);
    void deleteInquiry(int inquiryId);
    Inquiry getInquiryById(int inquiryId);
    List<Inquiry> getInquiriesByType(int typeId, int page, int limit);
    List<Inquiry> getAllInquiries(int page, int limit);
    int getListCount();
}
