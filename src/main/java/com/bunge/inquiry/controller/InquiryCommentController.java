package com.bunge.inquiry.controller;

import com.bunge.inquiry.domain.InquiryComment;
import com.bunge.inquiry.service.InquiryCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping( "/comments")
public class InquiryCommentController {
    @Autowired
    private InquiryCommentService commentService;

    @GetMapping("/{inquiryId}")
    public List<InquiryComment> getComments(@PathVariable Long inquiryId) {
        return commentService.findCommentsByInquiryId(inquiryId);
    }

    @PostMapping("/add")
    public InquiryComment addComment(@RequestBody InquiryComment comment) {
        int inserted = commentService.insertComment(comment);
        if (inserted > 0) {
            return comment;
        } else {
            throw new RuntimeException("Failed to add comment");
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Integer> deleteComment(@PathVariable Long commentId, @RequestParam(required = false) Long parentCommentId) {
        try {
            if (parentCommentId == null) {
                // 댓글 삭제
                commentService.deleteComment(commentId);
            } else {
                // 대댓글 삭제
                commentService.deleteReplyComment(commentId);
            }
            return ResponseEntity.ok(1); // 성공 시 1 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 실패 시 0 반환
        }
    }

    @PutMapping
    public ResponseEntity<String> updateComment(@RequestBody InquiryComment comment) {
        int updated = commentService.updateComment(comment);
        if (updated > 0) {
            return ResponseEntity.ok("Comment updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to update comment");
        }
    }
}


