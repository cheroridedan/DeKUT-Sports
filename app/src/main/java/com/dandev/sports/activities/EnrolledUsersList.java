package com.dandev.sports.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.dandev.sports.EnrolledUsers;
import com.dandev.sports.FirebaseDatabaseHelper;
import com.dandev.sports.R;
import com.dandev.sports.RecylerViewConfig;

import java.util.List;

public class EnrolledUsersList extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_users_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_enrolled);
        new FirebaseDatabaseHelper().readEnrolledUsers(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EnrolledUsers> enrolled, List<String> keys) {
                findViewById(R.id.loading).setVisibility(View.GONE);
                new RecylerViewConfig().setConfig(mRecyclerView,EnrolledUsersList.this, enrolled, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }
}