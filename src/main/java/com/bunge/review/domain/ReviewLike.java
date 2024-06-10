package com.bunge.review.domain;

public class ReviewLike {
    private int no;
    private int reviewno;
    private String id;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getReviewno() {
        return reviewno;
    }

    public void setReviewno(int reviewno) {
        this.reviewno = reviewno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReviewLike{" +
                "no=" + no +
                ", reviewno=" + reviewno +
                ", id='" + id + '\'' +
                '}';
    }
}
