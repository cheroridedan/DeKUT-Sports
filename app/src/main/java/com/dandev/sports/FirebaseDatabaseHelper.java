package com.dandev.sports;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceEnrolledUsers;
    private List<EnrolledUsers> enrolled = new ArrayList<>();

     public interface DataStatus{
         void DataIsLoaded(List<EnrolledUsers> enrolled, List<String> keys);
         void DataIsInserted();
         void DataIsUpdated();
         void DataIsDeleted();

     }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceEnrolledUsers = mDatabase.getReference("EnrolledUsers");
    }

    public void readEnrolledUsers(final DataStatus dataStatus)
    {
        mReferenceEnrolledUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                enrolled.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    EnrolledUsers enrolledUsers = keyNode.getValue(EnrolledUsers.class);
                    enrolled.add(enrolledUsers);
                }
                dataStatus.DataIsLoaded(enrolled,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
