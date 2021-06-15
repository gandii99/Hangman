package com.example.hangman;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hangman.api.HangmanScore;
import com.example.hangman.api.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScoreActivity extends AppCompatActivity implements MyAdapter.OnNoteListener, AdapterView.OnItemSelectedListener {

    List<Score> scoreList = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        recyclerView = findViewById(R.id.recycle_score);
        swipeRefreshLayout = findViewById(R.id.swiperefreshScoreActivity);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!haveNetwork()) {
                    download_scores();
                    putToRecyclerView();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if(haveNetwork()){
            download_scores();

            putToRecyclerView();
        }

    }

    private void putToRecyclerView(){
        MyAdapter myAdapter = new MyAdapter(ScoreActivity.this, scoreList,  ScoreActivity.this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ScoreActivity.this));
    }

    @Override
    public void onNoteClick(int position) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void download_scores(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hangmanservergandi.herokuapp.com/api/hangman/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        retrofit2.Call<List<HangmanScore>> call = jsonPlaceHolderApi.getScores();

        call.enqueue(new Callback<List<HangmanScore>>() {
            @Override
            public void onResponse(Call<List<HangmanScore>> call, Response<List<HangmanScore>> response) {

                for(int i=0; i<response.body().size(); i++){

                    scoreList.add( new Score(new Player(response.body().get(i).getName(), response.body().get(i).getAge()), response.body().get(i).getScore(),response.body().get(i).getDate() ) );
                }
                putToRecyclerView();
            }

            @Override
            public void onFailure(Call<List<HangmanScore>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Problem z połączeniem!", Toast.LENGTH_SHORT).show();
            }
        });
        return;
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
