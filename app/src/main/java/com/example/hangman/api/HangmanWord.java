package com.example.hangman.api;

public class HangmanWord {

    private Long id;
    private String word;
    private String category;

    public HangmanWord(String word, String category){
        this.word = word;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }

}
