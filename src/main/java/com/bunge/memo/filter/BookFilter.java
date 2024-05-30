package com.bunge.memo.filter;

public class BookFilter {
    private String isbn13;
    private String title;
    private String author;
    private String category;
    private Integer score;
    private Integer page;
    private int offset;
    private int limit;

    // Getters and Setters


    public String getIsbn13() { return isbn13; }

    public void setIsbn13(String isbn13) { this.isbn13 = isbn13; }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "BookFilter{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", score=" + score +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}