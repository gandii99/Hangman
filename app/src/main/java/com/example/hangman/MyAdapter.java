package com.example.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    private OnNoteListener mOnNoteListener;
    private List<Score> score;


    public MyAdapter(Context context, List<Score> score, OnNoteListener onNoteListener){
        this.context = context;
        this.score = score;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);


        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.namePlayer.setText(score.get(position).getPlayer().getName());
        holder.agePlayer.setText(String.valueOf(score.get(position).getPlayer().getAge()));
        holder.scorePlayer.setText(String.valueOf(score.get(position).getScore()));
        holder.datePlayer.setText(String.valueOf(score.get(position).getDate()));
    }

    @Override
    public int getItemCount(){
        return score.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView namePlayer, agePlayer, scorePlayer, datePlayer;
        OnNoteListener onNoteListener;
        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener){
            super(itemView);

            namePlayer = itemView.findViewById(R.id.namePlayerInput);
            agePlayer = itemView.findViewById(R.id.agePlayerInput);
            scorePlayer = itemView.findViewById(R.id.scorePlayerInput);
            datePlayer = itemView.findViewById(R.id.datePlayerInput);

            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            System.out.println("klik");
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
