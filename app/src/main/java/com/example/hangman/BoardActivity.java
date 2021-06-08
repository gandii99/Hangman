package com.example.hangman;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hangman.api.HangmanScore;
import com.example.hangman.api.JsonPlaceHolderApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    int score = 0;
    String wordRand = slowka[new Random().nextInt(75)];
    String allLetterUsed = "";
    String [] slowo = new String[wordRand.length()];
    int mistake = 0;
    boolean flaga = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8080/api/hangman/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        String name = sharedPreferences.getString("KEY_NAME", "noname");
        int age = sharedPreferences.getInt("KEY_AGE", 0);


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

                Call<Void> call = jsonPlaceHolderApi.addScore(name,age, score, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

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



            if (!Arrays.stream(slowo).anyMatch("_"::equals)) {
                result.setText("Gratulacje, wygrałeś!");
                playAgain.setVisibility(1);
                saveScore.setVisibility(1);
                score = new Random().nextInt(500);
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

            flaga = false;
            word.setText(String.join(" ", slowo));
            letterUsed.setText(allLetterUsed);
            chance.setText(String.valueOf(7 - mistake));
        }

    }


    private void updateImg(int play) {
        int resImg = getResources().getIdentifier("pic" + play, "drawable", getPackageName());
        pict.setImageResource(resImg);
    }
}
