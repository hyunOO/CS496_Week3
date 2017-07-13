package com.cs496.cs496_week3;

/**
 * Created by q on 2017-07-13.
 */

public class Room {
    private String title, maker, currentState;

    public void setTitle(String title){
        this.title = title;
    }
    public void setMaker(String maker){
        this.maker = maker;
    }
    public void setCurrentState(String currentState){
        this.currentState = currentState;
    }
    public String getTitle(){
        return this.title;
    }
    public String getMaker(){
        return this.maker;
    }
    public String getCurrentState(){
        return this.currentState;
    }
}
