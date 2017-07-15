package com.cs496.cs496_week3;

/**
 * Created by rongrong on 2017-07-15.
 */

public class User {
    private String id, department, circle, hobby;

    public void User(String id, String department, String circle, String hobby) {
        this.id = id;
        this.department = department;
        this.circle = circle;
        this.hobby = hobby;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getId() {
        return this.id;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getCircle() {
        return this.circle;
    }

    public String getHobby() {
        return this.hobby;
    }

}