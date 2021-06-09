package com.example.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SelectPlayerActivity extends AppCompatActivity {

    EditText name, age;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        swipeRefreshLayout = findViewById(R.id.swiperefreshSelectPlayerActivity);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!haveNetwork()) {
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if(haveNetwork()){
        }
    }

    public void savePlayer(View v){
        String namePlayer = name.getText().toString();
        int agePlayer = 0;
        if(age.getText().toString().length()>0){
            agePlayer = Integer.parseInt(age.getText().toString());
        }else{
            Toast.makeText(this, "Nie podano wieku!", Toast.LENGTH_SHORT).show();
        }


        if(namePlayer != null && namePlayer.length() > 0 && agePlayer != 0){
            Player player = new Player(namePlayer, agePlayer);
            SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("KEY_NAME", player.getName());
            editor.putInt("KEY_AGE", player.getAge());
            editor.apply();

            if(getIntent().getStringExtra("activity").equals("mainActivity")) {
                finish();
                startActivity(new Intent(SelectPlayerActivity.this, MainActivity.class));
            }else{
                finish();
                startActivity(new Intent(SelectPlayerActivity.this, BoardActivity.class));
            }
        }else{
            Toast.makeText(this, "Nie podano nazwy!", Toast.LENGTH_SHORT).show();
        }

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
