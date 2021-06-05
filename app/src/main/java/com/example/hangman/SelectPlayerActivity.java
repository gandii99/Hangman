package com.example.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SelectPlayerActivity extends AppCompatActivity {

    EditText name, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);

    }

    public void savePlayer(View v){
        String namePlayer = name.getText().toString();
        int agePlayer = 0;
        if(age.getText().toString().length()>0){
            agePlayer = Integer.parseInt(age.getText().toString());
        }


        if(namePlayer != null && namePlayer.length() > 0 && agePlayer != 0){
            System.out.println("taaak");
            Player player = new Player(namePlayer, agePlayer);
            SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("KEY_NAME", player.getName());
            editor.putInt("KEY_AGE", player.getAge());
            editor.apply();
            Intent intent = new Intent(SelectPlayerActivity.this, BoardActivity.class);
            startActivity(intent);
        }else{
            System.out.println("niee");
        }



    }

}
