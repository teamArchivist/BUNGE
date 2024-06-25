package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.InquiryComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InquiryCommentService {
    List<InquiryComment> findCommentsByInquiryId(Long inquiryId);
    InquiryComment findCommentById(Long commentId);
    int insertComment(InquiryComment comment);
    boolean deleteComment(Long commentId, String memberId);
    boolean deleteReplyComment(Long commentId, String memberId);
    int updateComment(InquiryComment comment,String memberId);
}
