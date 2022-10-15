package com.dandev.sports;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandev.sports.activities.DeleteActivity;
import com.dandev.sports.activities.EditActivity;
import com.dandev.sports.model.Activity;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class MyAdapterD extends RecyclerView.Adapter<MyAdapterD.MyView> {

    Context context;
    ArrayList<Activity> activityArrayList;

    public MyAdapterD(Context context, ArrayList<Activity> activityArrayList) {
        this.context = context;
        this.activityArrayList = activityArrayList;
    }

    @NonNull
    @Override
    public MyAdapterD.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.itemdelete,parent,false);
        return new MyView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterD.MyView holder, final int position) {

        Activity activity = activityArrayList.get(position);

        holder.gameName.setText(activity.getGameTitle());
        holder.gameVenue.setText(activity.getVenue());
        holder.gameTeams.setText(activity.getTeams());
        holder.gameDate.setText(activity.getDate());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.gameName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undone!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Events")
                                .child(activity.getNumber()).getRef().removeValue();
                        Toast.makeText(holder.gameName.getContext(), "Data deleted permanently.", Toast.LENGTH_SHORT).show();
                        ((DeleteActivity)context).finish();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.gameName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.show();


            }
        });



    }

    @Override
    public int getItemCount() {
        return activityArrayList.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView gameName,gameVenue,gameTeams,gameDate;
        Button btnDelete;

        public MyView(@NonNull View itemView) {
            super(itemView);

            gameName = itemView.findViewById(R.id.displayFootball);
            gameVenue = itemView.findViewById(R.id.displayVenue);
            gameTeams = itemView.findViewById(R.id.displayTeams);
            gameDate = itemView.findViewById(R.id.displayDate);

            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
