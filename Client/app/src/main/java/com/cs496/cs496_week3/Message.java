package com.cs496.cs496_week3;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Message {
    private Room room;
    private String status;

    public void Room(Room room, String status){
        this.room = room;
        this.status = status;
    }

    public void setRoom(Room room){
        this.room = room;
    }
    public void setStauts(String status){
        this.status = status;
    }
    public Room getRoom(){
        return this.room;
    }
    public String getStauts(){
        return this.status;
    }
}
