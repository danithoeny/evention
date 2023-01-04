package com.leoneer.evention;

import java.util.ArrayList;

public class Event {
    private String eventName;
    private String eventMode;
    private ArrayList<Game> eventGames;
    private ArrayList<Team> eventTeams;
    private ArrayList<Player> eventPlayers;
    private int pointsDraw;
    private int pointsWin;

    public Event(String eventName, String eventMode) {
        this.eventName = eventName;
        this.eventMode = eventMode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventMode() {
        return eventMode;
    }

    public void setEventMode(String eventMode) {
        this.eventMode = eventMode;
    }

    public ArrayList<Team> getEventTeams() {
        return eventTeams;
    }

    public void setEventTeams(ArrayList<Team> eventTeams) {
        this.eventTeams = eventTeams;
    }

    public ArrayList<Player> getEventPlayers() {
        return eventPlayers;
    }

    public void setEventPlayers(ArrayList<Player> eventPlayers) {
        this.eventPlayers = eventPlayers;
    }

    public int getPointsDraw() {
        return pointsDraw;
    }

    public void setPointsDraw(int pointsDraw) {
        this.pointsDraw = pointsDraw;
    }

    public int getPointsWin() {
        return pointsWin;
    }

    public void setPointsWin(int pointsWin) {
        this.pointsWin = pointsWin;
    }

    public ArrayList<Game> getEventGames() {
        return eventGames;
    }

    public void setEventGames(ArrayList<Game> eventGames) {
        this.eventGames = eventGames;
    }
}
