package com.bunge.inquiry.domain;

import java.sql.Timestamp;
import java.util.List;

public class Inquiry {
    private int inquiryId;
    private String memberId;
    private int typeId;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<InquiryAttachment> attachments;
    private List<InquiryComment> comments;

    // Getters and Setters
    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<InquiryAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<InquiryAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<InquiryComment> getComments() {
        return comments;
    }

    public void setComments(List<InquiryComment> comments) {
        this.comments = comments;
    }
}
