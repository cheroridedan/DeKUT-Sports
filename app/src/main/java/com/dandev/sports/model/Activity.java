package com.dandev.sports.model;

public class Activity {
    String gameTitle;
    String date;
    String venue;
    String teams;
    String number;

    public Activity(){}

    public Activity(String gameTitle, String date, String venue, String teams, String number) {
        this.gameTitle = gameTitle;
        this.date = date;
        this.venue = venue;
        this.teams = teams;
        this.number = number;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
