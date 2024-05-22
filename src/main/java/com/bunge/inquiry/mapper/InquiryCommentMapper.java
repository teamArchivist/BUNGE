package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.InquiryComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryCommentMapper {
    void insertComment(InquiryComment comment);
    List<InquiryComment> selectCommentsByInquiryId(int inquiryId);
    void deleteComment(@Param("commentId") int commentId, @Param("authorId") String authorId);
    void updateComment(InquiryComment comment);
}