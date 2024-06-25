package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.InquiryComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryCommentMapper {
    List<InquiryComment> findCommentsByInquiryId(Long inquiryId);

    InquiryComment findCommentById(@Param("commentId") Long commentId);

    int insertComment(InquiryComment comment);

    void deleteComment(@Param("commentId") Long commentId, @Param("memberId") String memberId);

    void deleteReplyComment(@Param("commentId") Long commentId, @Param("memberId") String memberId);

    int updateComment(@Param("comment") InquiryComment comment,@Param("memberId") String memberId);

}