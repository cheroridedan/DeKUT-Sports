package com.dandev.sports.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText gameTitle, gameVenue, gameTeam, gameDate;
    private Button btnPost;
    private DatePickerDialog picker;
    private DatabaseReference reference;
    private StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        gameTitle=findViewById(R.id.gameTitle);
        gameVenue=findViewById(R.id.gameVenue);
        gameDate = findViewById(R.id.gameTime);
        gameTeam=findViewById(R.id.gameTeam);
        btnPost=findViewById(R.id.btnPost);
        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(this);


        gameDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        gameDate.setText(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+ 1) + "/" + datePicker.getYear());


                        if (gameDate.getText().toString().isEmpty())
                        {
                            gameDate.setError("Enter venue");
                            gameDate.requestFocus();
                        }



                    }
                }, year, month, day);
                picker.show();

            }
        });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gameTitle.getText().toString().isEmpty())
                {
                    gameTitle.setError("Enter game name");
                    gameTitle.requestFocus();
                }

                if (gameVenue.getText().toString().isEmpty())
                {
                    gameVenue.setError("Enter venue");
                    gameVenue.requestFocus();
                }
                if(gameTeam.getText().toString().isEmpty())
                {
                    gameTeam.setError("Enter teams playing");
                    gameTeam.requestFocus();
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
        final String number = reference.push().getKey();

        String eventTitle = gameTitle.getText().toString();
        String eventVenue = gameVenue.getText().toString();
        String eventTeam = gameTeam.getText().toString();
        String eventTime = gameDate.getText().toString();



        Event event = new Event(eventTitle,eventTime,eventVenue, eventTeam,number);

        reference.child(number).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {





            @Override
            public void onSuccess(Void unused) {
                Log.d("TAG", "onSuccess: " + event );
                progressDialog.dismiss();


                Toast.makeText(AddActivity.this, "Event uploaded successfully", Toast.LENGTH_SHORT).show();
                finish();



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