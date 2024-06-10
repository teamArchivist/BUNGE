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

//        @Autowired
//        private EmailService emailService;

        @Override
        public List<InquiryComment> findCommentsByInquiryId(Long inquiryId) {
            return commentMapper.findCommentsByInquiryId(inquiryId);
        }

        @Override
        public List<InquiryComment> findRepliesByCommentId(Integer parentCommentId) {
            return commentMapper.findRepliesByCommentId(parentCommentId);
        }

    @Override
        public int insertComment(InquiryComment comment) {
//            notifyComment(comment.getInquiryId(), comment.getContent());
            return commentMapper.insertComment(comment);
        }

        @Override
        public void deleteComment(Long commentId) {
            commentMapper.deleteComment(commentId);
        }

        @Override
        public InquiryComment updateComment(InquiryComment comment) {
            commentMapper.updateComment(comment);
            return commentMapper.findCommentById(comment.getCommentId()); // 수정된 댓글 객체를 반환
        }

//        @Override
//        public void notifyComment(Long inquiryId, String commentContent) {
//            Inquiry inquiry = inquiryMapper.selectInquiry(inquiryId);
//            String email = inquiry.getEmail(); // 작성자 이메일
//            String subject = "새 댓글 알림";
//            String message = "문의글에 새로운 댓글이 달렸습니다: " + commentContent;

//            emailService.sendEmail(email, subject, message); // 이메일 발송
//        }
    }


