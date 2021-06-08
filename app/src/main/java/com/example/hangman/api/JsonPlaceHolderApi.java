package com.example.hangman.api;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("scores")
    Call<List<HangmanScore>> getScores();

    @POST("score")
    Call<Void> addScore(@Query("name") String name,
             @Query("age") int age,
             @Query("score") int score,
             @Query("date") String date);
}
