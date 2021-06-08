package com.example.hangman.api;

public class HangmanScore {

    private Long id;
    private String name;
    private int age;
    private int score;
    private String date;

    public HangmanScore(String name, int age, int score, String date) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }


}
