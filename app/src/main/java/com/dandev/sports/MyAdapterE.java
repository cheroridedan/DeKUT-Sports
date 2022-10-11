package com.dandev.sports;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandev.sports.model.Activity;
import com.dandev.sports.model.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapterE extends RecyclerView.Adapter<MyAdapterE.MyViewHolder> {
    Context context;
    ArrayList<Activity> activityArrayList;
    DatabaseReference reference;
//    ActivityUpdateDataBinding binding;

    public MyAdapterE(Context context, ArrayList<Activity> activityArrayList) {
        this.context = context;
        this.activityArrayList = activityArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Activity activity = activityArrayList.get(position);

        holder.gameName.setText(activity.getGameTitle());
        holder.gameVenue.setText(activity.getVenue());
        holder.gameTeams.setText(activity.getTeams());
        holder.gameDate.setText(activity.getDate());


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 850)
                        .create();



                View view1 = dialogPlus.getHolderView();

                EditText name = view1.findViewById(R.id.edGameName);
                EditText venue = view1.findViewById(R.id.edVenue);
                EditText teams = view1.findViewById(R.id.edTeams);
                EditText date = view1.findViewById(R.id.edDate);

                Button btnUpdate = view1.findViewById(R.id.btnUpdate);

                name.setText(activity.getGameTitle());
                venue.setText(activity.getVenue());
                teams.setText(activity.getTeams());
                date.setText(activity.getDate());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {






                        Map<String, Object> map = new HashMap<>();
                        map.put("gameTitle",name.getText().toString());
                        map.put("venue",venue.getText().toString());
                        map.put("teams",teams.getText().toString());
                        map.put("date",date.getText().toString());








                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {

        return activityArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{



        TextView gameName,gameVenue,gameTeams,gameDate;
        Button btnEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            gameName = itemView.findViewById(R.id.displayFootball);
            gameVenue = itemView.findViewById(R.id.displayVenue);
            gameTeams = itemView.findViewById(R.id.displayTeams);
            gameDate = itemView.findViewById(R.id.displayDate);

            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
