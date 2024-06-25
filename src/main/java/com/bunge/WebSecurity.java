package com.bunge;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.service.InquiryService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {

    private final InquiryService inquiryService;

    public WebSecurity(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    public boolean checkInquiryAccess(Authentication authentication, Long inquiryId) {
        Inquiry inquiry = inquiryService.getInquiryById(inquiryId);

        if (inquiry.isPrivate()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            return inquiry.getMemberId().equals(username) || isAdmin;
        }

        return true;
    }
}
