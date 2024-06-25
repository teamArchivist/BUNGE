package com.bunge.inquiry.service;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.domain.InquiryComment;
import com.bunge.inquiry.mapper.InquiryCommentMapper;
import com.bunge.inquiry.mapper.InquiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryCommentServiceImpl implements InquiryCommentService {

        @Autowired
        private InquiryCommentMapper commentMapper;

        @Autowired
        private InquiryMapper inquiryMapper;

        @Override
        public List<InquiryComment> findCommentsByInquiryId(Long inquiryId) {
            return commentMapper.findCommentsByInquiryId(inquiryId);
        }

        @Override
        public InquiryComment findCommentById(Long commentId) {
            return commentMapper.findCommentById(commentId);
        }

        @Override
        public int insertComment(InquiryComment comment) {
            return commentMapper.insertComment(comment);
        }

        @Override
        public boolean deleteComment(Long commentId, String memberId) {
            InquiryComment comment = commentMapper.findCommentById(commentId);
            if (comment != null && comment.getMemberId().equals(memberId)) {
                commentMapper.deleteComment(commentId, memberId);
                return true;
            }
            return false;
        }

        @Override
        public boolean deleteReplyComment(Long commentId, String memberId) {
            InquiryComment comment = commentMapper.findCommentById(commentId);
            if (comment != null && comment.getMemberId().equals(memberId)) {
                commentMapper.deleteReplyComment(commentId, memberId);
                return true;
            }
            return false;
        }

        @Override
        public int updateComment(InquiryComment comment, String memberId) {
            return commentMapper.updateComment(comment,comment.getMemberId());
        }
    }


