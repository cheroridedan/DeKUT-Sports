package com.dandev.sports.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.dandev.sports.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class QueryEnrolled extends AppCompatActivity {

    TextView tvEnrolled;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_enrolled);

        tvEnrolled = (TextView)findViewById(R.id.tvEnrolled);
        db = FirebaseFirestore.getInstance();


        fetchData();


    }

    private void fetchData() {

        db.collection("Users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots)
                        {
                            tvEnrolled.append(""+document.getString("FullName")+" : "+document.getString("Email")+"\n\n");
                        }

                    }
                });
    }
}