package com.leoneer.evention;

public class Game {
    private String team1;
    private String team2;
    private int score1;
    private int score2;
    private int pts1;
    private int pts2;
    private boolean ended;

    public Game(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getPts1() {
        return pts1;
    }

    public void setPts1(int pts1) {
        this.pts1 = pts1;
    }

    public int getPts2() {
        return pts2;
    }

    public void setPts2(int pts2) {
        this.pts2 = pts2;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}


