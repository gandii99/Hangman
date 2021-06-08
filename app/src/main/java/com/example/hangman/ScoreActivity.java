package com.example.hangman;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        download_scores();

        recyclerView = findViewById(R.id.recycle_score);
        putToRecyclerView();
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
                .baseUrl("http://192.168.0.14:8080/api/hangman/")
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
                return;
            }
        });
        return;
    }
}
