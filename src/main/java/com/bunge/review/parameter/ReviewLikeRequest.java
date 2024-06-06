package com.bunge.review.parameter;

public class ReviewLikeRequest {
    private int reviewno;
    private String id;

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
        return "ReviewLikeRequest{" +
                "reviewno=" + reviewno +
                ", id='" + id + '\'' +
                '}';
    }
}
