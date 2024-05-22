package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.InquiryComment;

import java.util.List;

public interface InquiryCommentService {
    void addComment(InquiryComment comment);
    List<InquiryComment> getCommentsByInquiryId(int inquiryId);
    void deleteComment(int commentId, String authorId);
    void updateComment(InquiryComment comment);
}
