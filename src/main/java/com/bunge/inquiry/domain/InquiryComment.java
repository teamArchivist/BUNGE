package com.bunge.inquiry.domain;

import java.sql.Timestamp;

public class InquiryComment {
    private Long commentId;
    private Long inquiryId;
    private String memberId;
    private Integer parentCommentId; // 부모 댓글 ID
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isNotified;

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setIsNotified(boolean notified) {
        isNotified = notified;
    }

    @Override
    public String toString() {
        return "InquiryComment{" +
                "commentId=" + commentId +
                ", inquiryId=" + inquiryId +
                ", memberId='" + memberId + '\'' +
                ", parentCommentId=" + parentCommentId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isNotified=" + isNotified +
                '}';
    }
}


