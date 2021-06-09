package com.example.hangman;


import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hangman.api.HangmanScore;
import com.example.hangman.api.HangmanWord;
import com.example.hangman.api.JsonPlaceHolderApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    List<String> slowka = new ArrayList<>(Arrays.asList("babcia", "badacz", "banita", "baszta", "bazalt", "beczka", "bilion", "biurko", "bluzka", "bogini", "bolero",
            "bonsai", "borsuk", "bosman", "bramka", "brzoza", "budowa", "busola", "bylina", "absmak", "absurd", "aceton", "adagio",
            "adonis", "adwent", "afazja", "afryka", "agenda", "agitka", "agonia", "agrest", "airbag", "akacja", "akcent", "akcept",
            "akcyza", "akonit", "akonto", "aktywa", "alalia", "alaska", "alegat", "alejka", "alians", "aliant", "alpaga", "altana",
            "aluzja", "amator", "amfora", "amorek", "amulet", "ananas", "angina", "angora", "anonim", "antena", "antyki", "aparat",
            "aplauz", "aplika", "apteka", "areszt", "arkada", "arnika", "aromat", "aronia", "asysta", "atleta", "atrapa", "aukcja", "avatar", "awaria", "awatar", "azalia", "azymut"));
    int score = 0;
    String wordRand = slowka.get(new Random().nextInt(75));
    String allLetterUsed = "";
    String [] slowo = new String[wordRand.length()];
    int mistake = 7;
    int secondsPassed = 0;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Retrofit retrofit;
    SwipeRefreshLayout swipeRefreshLayout;


    boolean flagTurnOffKayboard = false;
    boolean flagStartTimer = false;
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

        swipeRefreshLayout = findViewById(R.id.swiperefreshBoardActivity);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!haveNetwork()){
                    saveScore.setEnabled(false);
                }else{
                    saveScore.setEnabled(true);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if(haveNetwork()){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.14:8080/api/hangman/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<List<HangmanWord>> call = jsonPlaceHolderApi.getWords();

            call.enqueue(new Callback<List<HangmanWord>>() {
                @Override
                public void onResponse(Call<List<HangmanWord>> call, Response<List<HangmanWord>> response) {
                    for(int i = 0; i<response.body().size(); i++){
                        slowka.add(response.body().get(i).getWord());
                    }
                    wordRand = slowka.get(new Random().nextInt(slowka.size()-1));
                    slowo = new String[wordRand.length()];
                    String temp = "";
                    for(int i = 0; i < wordRand.length(); i++){
                        temp += "_ ";
                        slowo[i] = "_";
                    }
                    word.setText(temp);
                    for(int i = 0; i<slowo.length; i++){
                        System.out.println(slowo[i] + "totototu");
                    }
                }

                @Override
                public void onFailure(Call<List<HangmanWord>> call, Throwable t) {
                    return;
                }
            });
            saveScore.setEnabled(true);
        }else{
            wordRand = slowka.get(new Random().nextInt(slowka.size()-1));
            slowo = new String[wordRand.length()];
            String temp = "";
            for(int i = 0; i < wordRand.length(); i++){
                temp += "_ ";
                slowo[i] = "_";
            }
            word.setText(temp);
            saveScore.setEnabled(false);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        String name = sharedPreferences.getString("KEY_NAME", "noname");
        int age = sharedPreferences.getInt("KEY_AGE", 0);





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

        if(flagTurnOffKayboard == false){
            Button b = (Button) v;
            String letter = b.getText().toString().toLowerCase();
            if (!allLetterUsed.contains(letter)) {
                allLetterUsed += letter + ",";
            }

            if(wordRand.contains(letter)){
                for(int i = 0; i < wordRand.length(); i++){
                    if(wordRand.charAt(i) == letter.charAt(0)){
                        if(slowo[i] == "_"){
                            slowo[i] = letter;
                        }
                    }
                }
            }else{
                mistake--;
            }

            if(mistake == 0){
                result.setText("Przegrałeś, szukane słowo to: " + wordRand);
                playAgain.setVisibility(1);
                flagTurnOffKayboard = true;
                //TODO JEŻELI PRZEGRAŁEŚ

            }

            if(!Arrays.toString(slowo).contains("_")){
                result.setText("Gratulacje, wygrałeś!");
                playAgain.setVisibility(1);
                saveScore.setVisibility(1);
                score = new Random().nextInt(500);
                flagTurnOffKayboard = true;
                //TODO jeżeli wygrałeś
            }

            updateImg(mistake);
            v.setEnabled(false);
            word.setText(String.join(" ", slowo));
            letterUsed.setText(allLetterUsed);
            chance.setText(String.valueOf(mistake));
        }
    }


    private void updateImg(int play) {
        int resImg = getResources().getIdentifier("pic" + play, "drawable", getPackageName());
        pict.setImageResource(resImg);
    }

    private boolean haveNetwork(){
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos){
            if(info.getTypeName().equalsIgnoreCase("WIFI")){
                if(info.isConnected()) {
                    have_WIFI = true;
                }
            }
            if(info.getTypeName().equalsIgnoreCase("MOBILE")){
                if(info.isConnected()) {
                    have_MobileData = true;
                }
            }


        }
        if((have_MobileData || have_WIFI) == false){
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.no_internet_connection);
        }else{
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.internet_connection);
        }
        return have_MobileData || have_WIFI;
    }
}