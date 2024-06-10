package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.InquiryComment;

import java.util.List;

public interface InquiryCommentService {
    List<InquiryComment> findCommentsByInquiryId(Long inquiryId);
    List<InquiryComment> findRepliesByCommentId(Integer parentCommentId); // 대댓글 조회 메서드 추가
    int insertComment(InquiryComment comment);
    void deleteComment(Long commentId);
    int updateComment(InquiryComment comment);
}
