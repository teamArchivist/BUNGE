package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.InquiryComment;
import com.bunge.inquiry.mapper.InquiryCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryCommentServiceImpl implements InquiryCommentService {

    @Autowired
    private InquiryCommentMapper inquiryCommentMapper;

    @Override
    public void addComment(InquiryComment comment) {
        inquiryCommentMapper.insertComment(comment);
    }

    @Override
    public List<InquiryComment> getCommentsByInquiryId(int inquiryId) {
        return inquiryCommentMapper.selectCommentsByInquiryId(inquiryId);
    }

    @Override
    public void deleteComment(int commentId, String authorId) {
        inquiryCommentMapper.deleteComment(commentId, authorId);
    }

    @Override
    public void updateComment(InquiryComment comment) {
        inquiryCommentMapper.updateComment(comment);
    }
}
