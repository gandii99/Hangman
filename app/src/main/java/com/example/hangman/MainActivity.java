package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    Button newGame, score, exit, changePlayer;
    String name;
    TextView namePlayer;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGame = findViewById(R.id.newGame);
        score = findViewById(R.id.score);
        exit = findViewById(R.id.exit);
        changePlayer = findViewById(R.id.changePlayer);
        namePlayer = findViewById(R.id.namePlayer);
        swipeRefreshLayout = findViewById(R.id.swiperefreshMainActivity);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!haveNetwork()){
                    score.setEnabled(false);
                }else{
                    score.setEnabled(true);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        if(!haveNetwork()){
            score.setEnabled(false);
        }else{
            score.setEnabled(true);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        name = sharedPreferences.getString("KEY_NAME", "noname");
        if(name != "noname"){
            namePlayer.setText(name);
        }
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.equals("noname")){
                    Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, SelectPlayerActivity.class);
                    startActivity(intent);
                }

            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });

        changePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectPlayerActivity.class);
                intent.putExtra("activity", "mainActivity");
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
                System.exit(0);
            }
        });
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