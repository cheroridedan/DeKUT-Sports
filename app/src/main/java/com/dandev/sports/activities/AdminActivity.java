package com.dandev.sports.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dandev.sports.MainActivity;
import com.dandev.sports.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {



    Toolbar toolbar;
    CardView addPost;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar);
        addPost=findViewById(R.id.addEvent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Panel");

        mAuth = FirebaseAuth.getInstance();


        addPost.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.ic_logout:
                mAuth.signOut();
                finish();
                Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.ic_setting:
                Toast.makeText(this, "setting clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.addEvent:
                Intent intent = new Intent(AdminActivity.this,AddActivity.class);
                startActivity(intent);
                break;
        }

    }
}