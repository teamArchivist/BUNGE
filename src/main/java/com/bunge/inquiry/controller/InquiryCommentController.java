package com.bunge.inquiry.controller;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.domain.InquiryComment;
import com.bunge.inquiry.service.InquiryCommentService;
import com.bunge.inquiry.service.InquiryService;
import com.bunge.member.service.SendMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping( "/comments")
public class InquiryCommentController {

    private final SendMail sendMail;
    private SendMail sendmail;
    private final InquiryCommentService commentService;
    private final InquiryService inquiryService;

    @Autowired
    public InquiryCommentController(InquiryCommentService commentService,
                                    InquiryService inquiryService,
                                    SendMail sendmail, SendMail sendMail) {
        this.commentService = commentService;
        this.inquiryService = inquiryService;
        this.sendmail = sendmail;
        this.sendMail = sendMail;
    }

    @GetMapping("/{inquiryId}")
    public List<InquiryComment> getComments(@PathVariable Long inquiryId) {
        return commentService.findCommentsByInquiryId(inquiryId);
    }

    @PostMapping("/add")
    public Map<String, Object> addComment(@RequestBody InquiryComment comment, @AuthenticationPrincipal UserDetails userDetails) {
        int inserted = commentService.insertComment(comment);
        boolean isAnswered = false;
        if (inserted > 0) {
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("admin") || a.getAuthority().equals("superadmin"));
            if (isAdmin) {
                inquiryService.updateInquiryStatus(comment.getInquiryId(), true);
                isAnswered = true;

                // 메일 전송 로직 추가
                Inquiry inquiry = inquiryService.getInquiryById(comment.getInquiryId());
                if (inquiry != null && inquiry.getEmail() != null) {
                    System.out.println("메일 전송 준비 중: " + inquiry.getEmail()); // 디버그 로그 추가
                    sendMail.inquiryMail(inquiry.getEmail());
                } else {
                    System.out.println("이메일 정보를 찾을 수 없습니다."); // 디버그 로그 추가
                }
            }
        } else {
            throw new RuntimeException("Failed to add comment");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("comment", comment);
        response.put("isAnswered", isAnswered);
        return response;
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @RequestParam(required = false) Long parentCommentId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername(); // 로그인된 사용자 아이디

        InquiryComment existingComment = commentService.findCommentById(commentId);
        if (existingComment == null || !existingComment.getMemberId().equals(memberId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
        }

        try {
            if (parentCommentId == null) {
                // 댓글 삭제
                commentService.deleteComment(commentId, memberId);
            } else {
                // 대댓글 삭제
                commentService.deleteReplyComment(commentId, memberId);
            }
            return ResponseEntity.ok("Comment deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete comment");
        }
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId,
                                                @RequestBody InquiryComment comment,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername(); // 로그인된 사용자 아이디

        // 댓글의 작성자가 로그인된 사용자와 일치하는지 확인
        InquiryComment existingComment = commentService.findCommentById(commentId);
        if (existingComment == null || !existingComment.getMemberId().equals(memberId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
        }

        comment.setCommentId(commentId); // commentId를 설정
        comment.setMemberId(memberId); // memberId 설정
        int updated = commentService.updateComment(comment,memberId);
        if (updated > 0) {
            return ResponseEntity.ok("Comment updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to update comment");
        }
    }

}

