package com.cs496.cs496_week3;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Message {
    private String title, requester, bangjang, roomId;
    private boolean bangjangRead, accept;
    private boolean isNew = false;

    public void Message(){

    }

    public Message(String roomId, String title, String requester, String bangjang, boolean bangjangRead, boolean accept) {
        this.roomId = roomId;
        this.title = title;
        this.requester = requester;
        this.bangjang = bangjang;
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

    public void setBangjangRead(Boolean bangjangRead) {
        this.bangjangRead = bangjangRead;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public boolean getIsNew(){
        return this.isNew;
    }

    public String getTitle() {
        return this.title;
    }

    public String getRequester(){
        return this.requester;
    }

    public String getBangjang() {
        return this.bangjang;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public boolean getBanjangRead(){
        return this.bangjangRead;
    }

    public boolean getAccept(){
        return this.accept;
    }
}
