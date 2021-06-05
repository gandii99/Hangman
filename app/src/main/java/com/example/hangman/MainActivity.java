package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button newGame, score, exit, changePlayer;
    String name;
    TextView namePlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGame = findViewById(R.id.newGame);
        score = findViewById(R.id.score);
        exit = findViewById(R.id.exit);
        changePlayer = findViewById(R.id.changePlayer);
        namePlayer = findViewById(R.id.namePlayer);
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
}