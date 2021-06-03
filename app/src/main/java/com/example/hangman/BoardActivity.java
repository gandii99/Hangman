package com.example.hangman;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class BoardActivity extends AppCompatActivity {

    TextView word, letterUsed, chance, result;
    ImageView pict;
    LinearLayout linearLayout;

    String wordRand = "Bangladesz".toLowerCase();
    String allLetterUsed = "";
    String [] slowo = new String[wordRand.length()];
    int mistake = 0;
    boolean flaga = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        word = findViewById(R.id.wordTv);
        letterUsed = findViewById(R.id.wordToFindTv);
        pict = findViewById(R.id.img);
        chance = findViewById(R.id.chance);
        result = findViewById(R.id.result);
        linearLayout = findViewById(R.id.containerLetter);

        String temp = "";
        for(int i = 0; i < wordRand.length(); i++){
            temp += "_ ";
            slowo[i] = "_";
        }
        word.setText(temp);
    }

    public void touchLetter(View v){
        if(mistake < 7) {
            Button b = (Button) v;
            String letter = b.getText().toString().toLowerCase();
            if (!allLetterUsed.contains(letter)) {
                allLetterUsed += letter + ",";
            }

            letterUsed.setText(allLetterUsed);

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

            word.setText(String.join(" ", slowo));

            if (!Arrays.stream(slowo).anyMatch("_"::equals)) {
                result.setText("Gratulacje, wygrałeś!");
            }
            if (mistake == 6) {
                result.setText("Bardzo się starałeś lecz z gry wyleciałeś! \n Słowo, którego szukałeś to: " + wordRand);
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View view = linearLayout.getChildAt(i);
                    view.setEnabled(false);
                }
                //TODO co jeżeli przegrałeś
            }
            if (flaga == false) {
                mistake++;
                updateImg(mistake);
            }
            chance.setText(String.valueOf(7 - mistake));
            flaga = false;
        }
    }

    private void updateImg(int play) {
        int resImg = getResources().getIdentifier("pic" + play, "drawable", getPackageName());
        pict.setImageResource(resImg);
    }
}
