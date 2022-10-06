package com.dandev.sports.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dandev.sports.R;
import com.dandev.sports.model.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity {

    private EditText gameTitle, gameVenue, gameTeam;
    private Button btnPost;
    private DatabaseReference reference;
    private StorageReference storageReference;
    String downloadUrl = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        gameTitle=findViewById(R.id.gameTitle);
        gameVenue=findViewById(R.id.gameVenue);
        gameTeam=findViewById(R.id.gameTeam);
        btnPost=findViewById(R.id.btnPost);
        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(this);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gameTitle.getText().toString().isEmpty())
                {
                    gameTitle.setError("Enter game name");
                    gameTitle.requestFocus();
                }
                if(gameTeam.getText().toString().isEmpty())
                {
                    gameTeam.setError("Enter teams playing");
                    gameTeam.requestFocus();
                }
                if (gameVenue.getText().toString().isEmpty())
                {
                    gameVenue.setError("Enter venue");
                    gameVenue.requestFocus();
                }

                 else
                {

                    postData();
                    progressDialog.setMessage("Uploading Data...");
                    progressDialog.setTitle("Add Event");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                }


            }
        });


    }

    private void postData() {

        reference=reference.child("Events");
        final String uniqueKey = reference.push().getKey();

        String eventTitle = gameTitle.getText().toString();
        String eventVenue = gameVenue.getText().toString();
        String eventTeam = gameTeam.getText().toString();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd-MM-yy");

        String currentDate = simpleDateFormat.format(calendar.getTime());


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat( "hh:mm a");

        String time = sdf.format(c.getTime());


        Event event = new Event(eventTitle,currentDate,eventVenue, eventTeam,uniqueKey);

        reference.child(uniqueKey).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {





            @Override
            public void onSuccess(Void unused) {
                Log.d("TAG", "onSuccess: " + event );
                progressDialog.dismiss();


                Toast.makeText(AddActivity.this, "Event uploaded successfully", Toast.LENGTH_SHORT).show();



            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(AddActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });



    }
}