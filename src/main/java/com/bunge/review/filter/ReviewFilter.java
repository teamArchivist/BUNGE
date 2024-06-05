package com.bunge.review.filter;

public class ReviewFilter {
    private int no;
    private int reviewno;
    private String booktitle;
    private String id;
    private String linetitle;
    private String content;
    private int score;
    private Integer page;
    private int offset;
    private int limit;

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

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinetitle() {
        return linetitle;
    }

    public void setLinetitle(String linetitle) {
        this.linetitle = linetitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "ReviewFilter{" +
                "no=" + no +
                ", reviewno=" + reviewno +
                ", booktitle='" + booktitle + '\'' +
                ", id='" + id + '\'' +
                ", linetitle='" + linetitle + '\'' +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
