package com.bunge.study.domain;

public class Study {
    private int no;
    private String id;
    private String title;
    private String content;
    private String startdate;
    private String enddate;
    private String challengestart;
    private String challengeend;
    private int quota;
    private int state;
    private int readcount;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getChallengestart() {
        return challengestart;
    }

    public void setChallengestart(String challengestart) {
        this.challengestart = challengestart;
    }

    public String getChallengeend() {
        return challengeend;
    }

    public void setChallengeend(String challengeend) {
        this.challengeend = challengeend;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getReadcount() {
        return readcount;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
    }

    @Override
    public String toString() {
        return "Study{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", challengestart='" + challengestart + '\'' +
                ", challengeend='" + challengeend + '\'' +
                ", quota=" + quota +
                ", state=" + state +
                ", readcount=" + readcount +
                '}';
    }
}
