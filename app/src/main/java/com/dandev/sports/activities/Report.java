package com.dandev.sports.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dandev.sports.R;

public class Report extends AppCompatActivity implements View.OnClickListener {

    CardView users, enrolled, payments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        enrolled = findViewById(R.id.enrolled);

        enrolled.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.enrolled:
//                intent = new Intent(Report.this,JoinedUsers.class);
//                startActivity(intent);
                break;

            default:break;
        }
    }
}