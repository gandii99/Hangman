package com.example.hangman;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Score {
    private Player player;
    private int score;
    private String date;

    Score(Player player, int score){
        this.player = player;
        this.score = score;
        this.date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

}
