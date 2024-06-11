package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.InquiryComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryCommentMapper {
    List<InquiryComment> findCommentsByInquiryId(Long inquiryId);

    int insertComment(InquiryComment comment);

    int deleteComment(Long commentId);

    int deleteReplyComment(Long commentId);

    int updateComment(InquiryComment comment);

}