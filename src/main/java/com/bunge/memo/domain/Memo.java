package com.bunge.memo.domain;

public class Memo {
    private int no;
    private String id;
    private int readpage;
    private int remainpage;
    private String ispublic;
    private String isbn13;
    private String cover;
    private String title;
    private String keyword;
    private String content;
    private String created;
    private String lastmodified;

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

    public int getReadpage() {
        return readpage;
    }

    public void setReadpage(int readpage) {
        this.readpage = readpage;
    }

    public int getRemainpage() {
        return remainpage;
    }

    public void setRemainpage(int remainpage) {
        this.remainpage = remainpage;
    }

    public String getIspublic() {
        return ispublic;
    }

    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public String getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(String lastmodified) {
        this.lastmodified = lastmodified;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", readpage=" + readpage +
                ", remainpage=" + remainpage +
                ", ispublic='" + ispublic + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", keyword='" + keyword + '\'' +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", lastmodified='" + lastmodified + '\'' +
                '}';
    }
}
