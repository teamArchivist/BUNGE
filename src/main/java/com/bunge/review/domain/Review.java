package com.bunge.review.domain;

public class Review {
    private int no;
    private String id;
    private String isbn13;
    private String cover;
    private String linetitle;
    private String content;
    private int score;
    private String created;
    private String lastmodified;
    private int challengeperiod;

    private int countcomment;

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

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(String lastmodified) {
        this.lastmodified = lastmodified;
    }

    public int getChallengeperiod() {
        return challengeperiod;
    }

    public void setChallengeperiod(int challengeperiod) {
        this.challengeperiod = challengeperiod;
    }

    public int getCountcomment() {
        return countcomment;
    }

    public void setCountcomment(int countcomment) {
        this.countcomment = countcomment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", cover='" + cover + '\'' +
                ", linetitle='" + linetitle + '\'' +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", created='" + created + '\'' +
                ", lastmodified='" + lastmodified + '\'' +
                ", challengeperiod=" + challengeperiod +
                ", countcomment=" + countcomment +
                '}';
    }
}
