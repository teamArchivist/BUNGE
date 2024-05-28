package com.bunge.memo.domain;

public class Book {

    private int no;
    private String title;
    private String author;
    private String pubdate;
    private String category;
    private String description;
    private int score;
    private String cover;
    private String regitdate;


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRegitdate() { return regitdate; }

    public void setRegitdate(String regitdate) { this.regitdate = regitdate; }

    @Override
    public String toString() {
        return "Book{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                ", cover='" + cover + '\'' +
                ", regitdate='" + regitdate + '\'' +
                '}';
    }
}
