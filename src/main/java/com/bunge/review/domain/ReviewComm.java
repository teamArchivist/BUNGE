package com.bunge.review.domain;

public class ReviewComm {
    private int no;
    private String id;
    private int reviewno;
    private String content;
    private String created;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReviewno() {
        return reviewno;
    }

    public void setReviewno(int reviewno) {
        this.reviewno = reviewno;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ReviewComm{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", reviewno=" + reviewno +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
