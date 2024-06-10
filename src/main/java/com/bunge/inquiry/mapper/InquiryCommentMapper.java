package com.bunge.inquiry.mapper;

import com.bunge.inquiry.domain.InquiryComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryCommentMapper {
    List<InquiryComment> findCommentsByInquiryId(Long inquiryId);

    List<InquiryComment> findRepliesByCommentId(Integer parentCommentId); // 대댓글 조회 메서드 추가

    InquiryComment findCommentById(Long commentId); // 특정 댓글 조회 메서드 추가

    int insertComment(InquiryComment comment);

    void deleteComment(Long commentId);

    int updateComment(InquiryComment comment);

}