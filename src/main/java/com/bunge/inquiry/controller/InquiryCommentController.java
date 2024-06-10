package com.bunge.inquiry.controller;

import com.bunge.inquiry.domain.InquiryComment;
import com.bunge.inquiry.service.InquiryCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/replies/{parentCommentId}")
    public List<InquiryComment> getRepliesByCommentId(@PathVariable int parentCommentId) {
        return commentService.findRepliesByCommentId(parentCommentId);
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

    @DeleteMapping("/delete{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping
    public InquiryComment updateComment(@RequestBody InquiryComment comment) {
        return commentService.updateComment(comment);
    }
}


