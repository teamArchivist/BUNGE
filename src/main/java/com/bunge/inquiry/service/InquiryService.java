package com.bunge.inquiry.service;

import java.util.List;

import com.bunge.inquiry.domain.Inquiry;
import org.springframework.web.multipart.MultipartFile;

public interface InquiryService {
    void addInquiry(Inquiry inquiry);
    int updateInquiry(Inquiry inquiry);
    void deleteInquiry(Long inquiryId);
    Inquiry getInquiryById(Long inquiryId);
    List<Inquiry> getAllInquiries(int limit, int offset);
    List<Inquiry> getInquiriesByType(int typeId, int limit, int offset);
    int getInquiryCount();
    Inquiry getView(Long inquiryId);

    List<Inquiry> getmyinquiry(String id);

    int getMyinquirtListCount(String id);
}

