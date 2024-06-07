package com.bunge.review.parameter;

public class ReviewCommDeleteRequest {
    int no;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "ReviewCommDeleteRequest{" +
                "no=" + no +
                '}';
    }
}
