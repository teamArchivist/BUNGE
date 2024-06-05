package com.bunge.inquiry.domain;

import java.sql.Timestamp;

public class InquiryComment {
    private int commentId;
    private int inquiryId;
    private boolean AdminId;
    private String content;
    private Timestamp createdAt;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAdminId() {
        return AdminId;
    }

    public void setAdminId(boolean adminId) {
        AdminId = adminId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
