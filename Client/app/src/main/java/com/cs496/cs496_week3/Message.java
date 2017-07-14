package com.cs496.cs496_week3;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Message {
    private String title, requester, bangjang, content;
    private Boolean requesterRead, bangjangRead, accept;

    public void Message(String title, String requester, String bangjang, Boolean requesterRead, Boolean bangjangRead, Boolean accept) {
        this.title = title;
        this.requester = requester;
        this.bangjang = bangjang;
        this.requesterRead = requesterRead;
        this.bangjangRead = bangjangRead;
        this.accept = accept;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public void setBangjang(String bangjang) {
        this.bangjang = bangjang;
    }

    public void setRequesterRead(Boolean requesterRead) {
        this.requesterRead = requesterRead;
    }

    public void setBangjangRead(Boolean bangjangRead) {
        this.bangjangRead = bangjangRead;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFrom() {
        return this.bangjang;
    }

    public String getContent() {
        return this.content;
    }
}
