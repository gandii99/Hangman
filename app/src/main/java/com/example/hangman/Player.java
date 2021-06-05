package com.example.hangman;

public class Player {
    private String name;
    private int age;

    Player(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
