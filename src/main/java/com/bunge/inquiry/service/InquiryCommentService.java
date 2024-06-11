package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.InquiryComment;

import java.util.List;

public interface InquiryCommentService {
    List<InquiryComment> findCommentsByInquiryId(Long inquiryId);
    int insertComment(InquiryComment comment);
    int deleteComment(Long commentId);
    int deleteReplyComment(Long commentId);
    int updateComment(InquiryComment comment);
}
