package com.example.hangman;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScoreActivity extends AppCompatActivity implements MyAdapter.OnNoteListener, AdapterView.OnItemSelectedListener {

    List<Score> scoreList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        recyclerView = findViewById(R.id.recycle_score);
        Player a = new Player("Kasia", 32);
        Player b = new Player("Jarek", 41);
        Player c = new Player("Paweł", 21);

        scoreList.add(new Score(a, 123));
        scoreList.add(new Score(b, 321));
        scoreList.add(new Score(c, 100));
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
}
