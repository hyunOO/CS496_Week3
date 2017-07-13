package com.cs496.cs496_week3;

import java.util.ArrayList;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Room {
    private String title, makerId, current, mealType;

    public void Room(String title, String makerId, String mealType) {
        this.title = title;
        this.makerId = makerId;
        this.mealType = mealType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMakerId(String makerId) {
        this.makerId = makerId;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setCurrent(Integer maxUser, Integer currentUser) {
        this.current = String.valueOf(currentUser) + "/" + String.valueOf(maxUser);
    }

    public String getTitle() {
        return this.title;
    }

    public String getMakerId() {
        return this.makerId;
    }

    public String getMealType() {
        return this.mealType;
    }

    public String getCurrent() {
        return current;
    }
}
