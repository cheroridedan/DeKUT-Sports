package com.dandev.sports;

public class EnrolledUsers {
    private String category, game, gender, phoneNumber, regNo;

    public EnrolledUsers() {
    }

    public EnrolledUsers(String category, String game, String gender, String phoneNumber, String regNo) {
        this.category = category;
        this.game = game;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.regNo = regNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}
