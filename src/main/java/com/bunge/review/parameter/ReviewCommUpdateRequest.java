package com.bunge.review.parameter;

public class ReviewCommUpdateRequest {
    private int no;
    private String content;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReviewCommUpdateRequest{" +
                "no=" + no +
                ", content='" + content + '\'' +
                '}';
    }
}
