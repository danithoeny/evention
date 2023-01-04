package com.leoneer.evention;

import java.util.ArrayList;

public class Team implements Comparable<Team>{
    private String teamName;
    private ArrayList<Player> teamPlayers;
    private int teamFor;
    private int teamAgainst;
    private int teamDiff;
    private int teamPoints;
    private boolean teamActive;

    public Team(String teamName, ArrayList<Player> teamPlayers, boolean teamActive) {
        this.teamName = teamName;
        this.teamPlayers = teamPlayers;
        this.teamActive = teamActive;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(ArrayList<Player> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public int getTeamPoints() {
        return teamPoints;
    }

    public void setTeamPoints(int teamPoints) {
        this.teamPoints = teamPoints;
    }

    public boolean isTeamActive() {
        return teamActive;
    }

    public void setTeamActive(boolean teamActive) {
        this.teamActive = teamActive;
    }

    public int getTeamFor() {
        return teamFor;
    }

    public void setTeamFor(int teamFor) {
        this.teamFor = teamFor;
    }

    public int getTeamAgainst() {
        return teamAgainst;
    }

    public void setTeamAgainst(int teamAgainst) {
        this.teamAgainst = teamAgainst;
    }

    public int getTeamDiff() {
        return teamDiff;
    }

    public void setTeamDiff(int teamDiff) {
        this.teamDiff = teamDiff;
    }

    @Override
    public int compareTo(Team other) {
        int pointsDiff = this.getTeamPoints() - other.getTeamPoints();
        if(pointsDiff != 0){
            return pointsDiff;
        }

        int diffDiff =  this.getTeamDiff() - other.getTeamDiff();
        if(diffDiff != 0){
            return diffDiff;
        }

        int forDiff = this.getTeamFor() - other.getTeamFor();
        if (forDiff != 0){
            return forDiff;
        }

        return other.getTeamAgainst() - this.getTeamAgainst();
    }
}

