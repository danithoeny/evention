package com.leoneer.evention;

public class Player {
    private String playerName;
    private int playerPoints;
    private boolean playerActive;
    private boolean inATeam;

    public Player(String playerName, boolean playerActive, boolean inATeam) {
        this.playerName = playerName;
        this.playerActive = playerActive;
        this.inATeam = inATeam;
    }

    public String getPlayerName() {return playerName;}

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public boolean isPlayerActive() {
        return playerActive;
    }

    public void setPlayerActive(boolean playerActive) {
        this.playerActive = playerActive;
    }

    public boolean isInATeam() {return inATeam;}

    public void setInATeam(boolean inATeam) {
        this.inATeam = inATeam;
    }
}
