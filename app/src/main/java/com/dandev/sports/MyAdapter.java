package com.dandev.sports;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandev.sports.activities.EditActivity;
import com.dandev.sports.activities.FootballActivity;
import com.dandev.sports.activities.ForgotPasswordActivity;
import com.dandev.sports.activities.LoginActivity;
import com.dandev.sports.activities.Payment;
import com.dandev.sports.model.Games;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    static Context context;

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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView gameName,gameVenue,gameTeams,gameDate;
        Button btnJoin, btnPay;
        EditText edRegNo, edPhoneNumber, edGender, edGame, edCategory;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child("EnrolledUsers");

        final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.participate_popup))
                .setExpanded(true, 1000)
                .create();




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            gameName = itemView.findViewById(R.id.displayFootball);
            gameVenue = itemView.findViewById(R.id.displayVenue);
            gameTeams = itemView.findViewById(R.id.displayTeams);
            gameDate = itemView.findViewById(R.id.displayDate);

            btnJoin  = itemView.findViewById(R.id.btnJoin);
            btnPay  = itemView.findViewById(R.id.btnPay);


            btnJoin.setOnClickListener(this);
            btnPay.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {



            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(context, Payment.class);
                    context.startActivity(intent);

                }
            });

            showDialog(view);
        }

        private void showDialog(View view)
        {


            View view1 = dialogPlus.getHolderView();

            edRegNo = view1.findViewById(R.id.edRegNo);
            edPhoneNumber = view1.findViewById(R.id.edPhoneNumber);
            edGender = view1.findViewById(R.id.edGender);
            edGame = view1.findViewById(R.id.edGame);
            edCategory = view1.findViewById(R.id.edCategory);




            Button btnJoinEvent = view1.findViewById(R.id.btnJoinEvent);

            btnJoinEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String regNo = edRegNo.getText().toString().trim();
                    String phoneNumber = edPhoneNumber.getText().toString().trim();
                    String gender = edGender.getText().toString().trim();
                    String game = edGame.getText().toString().trim();
                    String category = edCategory.getText().toString().trim();

                    if (!TextUtils.isEmpty(regNo)){
                        if (phoneNumber.length() == 10){
                            if (!TextUtils.isEmpty(gender)){
                                if (!TextUtils.isEmpty(game)){
                                    if (!TextUtils.isEmpty(category)){
                                        JoinEvent();

                                    }else {
                                        edCategory.setError("Enter Category.");
                                    }
                                }else {
                                    edGame.setError("Enter Game Name.");
                                }
                            }else {
                                edGender.setError("Enter Your Gender.");
                            }
                        }else {
                            edPhoneNumber.setError("Enter Correct Phone Number.");
                        }

                    }else {
                        edRegNo.setError("Enter Valid Reg. No.");
                    }

                }
            });

        dialogPlus.show();

        }

        private void JoinEvent()  {

            String regNo = edRegNo.getText().toString().trim();
            String phoneNumber = edPhoneNumber.getText().toString().trim();
            String gender = edGender.getText().toString().trim();
            String game = edGame.getText().toString().trim();
            String category = edCategory.getText().toString().trim();

            HashMap<String, String> enrollMap = new HashMap<>();

            enrollMap.put("regNo", regNo);
            enrollMap.put("phoneNumber", phoneNumber);
            enrollMap.put("gender", gender);
            enrollMap.put("game", game);
            enrollMap.put("category", category);

            root.push().setValue(enrollMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(context, "Joined Successfully.", Toast.LENGTH_SHORT).show();
                    dialogPlus.dismiss();
                    btnJoin.setText("Joined");
                    btnJoin.setEnabled(false);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(context, "Error while joining.", Toast.LENGTH_SHORT).show();
                    dialogPlus.dismiss();
                    btnJoin.setEnabled(false);
                    btnJoin.setText("Joined");

                }
            });






        }

    }




}
