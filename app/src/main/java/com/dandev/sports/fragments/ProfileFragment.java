package com.dandev.sports.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dandev.sports.R;
import com.dandev.sports.activities.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firestore.v1.TransactionOptions;

import java.util.HashMap;


public class ProfileFragment extends Fragment {
    EditText fullName, email, password, userName;
    Button update;
    DatabaseReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        userName = view.findViewById(R.id.userName);
        update = view.findViewById(R.id.update);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");


        onStart();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();

            }
        });


        return view;
    }

    private void updateProfile() {

        String name = fullName.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = user.getUid();

        DocumentReference sDoc = db.collection("Users").document(currentId);

        db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {

                        transaction.update(sDoc, "FullName",name);
                        transaction.update(sDoc, "Email",mail);
                        transaction.update(sDoc, "Password",pass);

                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

                    }
                });



    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = user.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference = firestore.collection("Users").document(currentId);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists())
                        {

                            String nameresult = task.getResult().getString("FullName");
                            String emailresult = task.getResult().getString("Email");
                            String passwordresult = task.getResult().getString("Password");

                            fullName.setText(nameresult);
                            userName.setText(nameresult);
                            email.setText(emailresult);
                            password.setText(passwordresult);


                        }
                        else
                        {

                            Intent intent = new Intent(getActivity(), RegisterActivity.class);
                            startActivity(intent);
                        }

                    }
                });

    }
}


