package com.dandev.sports.model;

public class Activity {
    String gameTitle;
    String date;
    String venue;
    String teams;
    String uniqueKey;

    public Activity(){}

    public Activity(String gameTitle, String date, String venue, String teams, String uniqueKey) {
        this.gameTitle = gameTitle;
        this.date = date;
        this.venue = venue;
        this.teams = teams;
        this.uniqueKey = uniqueKey;
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

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
