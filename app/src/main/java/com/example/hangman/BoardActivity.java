package com.example.hangman;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoardActivity extends AppCompatActivity {

    TextView word, letterUsed, chance, result;
    ImageView pict;
    LinearLayout linearLayout;
    Button playAgain, saveScore;
    String [] slowka = {"babcia", "badacz", "banita", "baszta", "bazalt", "beczka", "bilion", "biurko", "bluzka", "bogini", "bolero",
            "bonsai", "borsuk", "bosman", "bramka", "brzoza", "budowa", "busola", "bylina", "absmak", "absurd", "aceton", "adagio",
            "adonis", "adwent", "afazja", "afryka", "agenda", "agitka", "agonia", "agrest", "airbag", "akacja", "akcent", "akcept",
            "akcyza", "akonit", "akonto", "aktywa", "alalia", "alaska", "alegat", "alejka", "alians", "aliant", "alpaga", "altana",
            "aluzja", "amator", "amfora", "amorek", "amulet", "ananas", "angina", "angora", "anonim", "antena", "antyki", "aparat",
            "aplauz", "aplika", "apteka", "areszt", "arkada", "arnika", "aromat", "aronia", "asysta", "atleta", "atrapa", "aukcja", "avatar", "awaria", "awatar", "azalia", "azymut"};

    String wordRand = slowka[new Random().nextInt(75)];
    String allLetterUsed = "";
    String [] slowo = new String[wordRand.length()];
    int mistake = 0;
    boolean flaga = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        word = findViewById(R.id.wordTv);
        letterUsed = findViewById(R.id.wordToFindTv);
        pict = findViewById(R.id.img);
        chance = findViewById(R.id.chance);
        result = findViewById(R.id.result);
        playAgain = findViewById(R.id.playAgain);
        saveScore = findViewById(R.id.saveScore);
        linearLayout = findViewById(R.id.containerLetter);



        String temp = "";
        for(int i = 0; i < wordRand.length(); i++){
            temp += "_ ";
            slowo[i] = "_";
        }
        word.setText(temp);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardActivity.this, BoardActivity.class);
                finish();
                startActivity(intent);
            }
        });

        saveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });
    }

    public void touchLetter(View v){
        if(mistake < 7 && Arrays.stream(slowo).anyMatch("_"::equals)) {
            Button b = (Button) v;
            String letter = b.getText().toString().toLowerCase();
            if (!allLetterUsed.contains(letter)) {
                allLetterUsed += letter + ",";
            }

            letterUsed.setText(allLetterUsed);

            if (wordRand.contains(letter.toLowerCase())) {
                for (int i = 0; i < wordRand.length(); i++) {
                    if (wordRand.charAt(i) == letter.charAt(0)) {
                        if (slowo[i] == "_") {
                            slowo[i] = letter;
                            flaga = true;
                            break;
                        }
                    }
                }
            } else {
                flaga = false;
            }

            word.setText(String.join(" ", slowo));

            if (!Arrays.stream(slowo).anyMatch("_"::equals)) {
                result.setText("Gratulacje, wygrałeś!");
                playAgain.setVisibility(1);
                saveScore.setVisibility(1);
            }
            if (mistake == 6) {
                result.setText("Bardzo się starałeś lecz z gry wyleciałeś! \n Słowo, którego szukałeś to: " + wordRand);
                playAgain.setVisibility(1);
                //TODO co jeżeli przegrałeś
            }
            if (flaga == false) {
                mistake++;
                updateImg(mistake);
            }
            chance.setText(String.valueOf(7 - mistake));
            flaga = false;
        }
    }


    private void updateImg(int play) {
        int resImg = getResources().getIdentifier("pic" + play, "drawable", getPackageName());
        pict.setImageResource(resImg);
    }
}
