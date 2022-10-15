package com.dandev.sports;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandev.sports.model.Games;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<Games> list;

    public MyAdapter(Context context, ArrayList<Games> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v  = LayoutInflater.from(context).inflate(R.layout.gametype,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Games games = list.get(position);

        holder.gameName.setText(games.getGameTitle());
        holder.gameVenue.setText(games.getVenue());
        holder.gameTeams.setText(games.getTeams());
        holder.gameDate.setText(games.getDate());




    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView gameName,gameVenue,gameTeams,gameDate;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            gameName = itemView.findViewById(R.id.displayFootball);
            gameVenue = itemView.findViewById(R.id.displayVenue);
            gameTeams = itemView.findViewById(R.id.displayTeams);
            gameDate = itemView.findViewById(R.id.displayDate);




        }
    }



}
