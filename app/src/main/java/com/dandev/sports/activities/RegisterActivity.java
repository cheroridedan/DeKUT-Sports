package com.dandev.sports.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dandev.sports.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveAnAccount;
    EditText inputEmail, inputPassword, inputConfirmPassword, inputName;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;


    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        fStore =FirebaseFirestore.getInstance();


        intializeFields();


        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });


    }

    private void intializeFields() {


        inputEmail = findViewById(R.id.inputEmail);
        inputName = findViewById(R.id.etName);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);


        alreadyHaveAnAccount = findViewById(R.id.alreadyHaveAnAccount);
    }


    private void performAuth() {
        String email = inputEmail.getText().toString();
        String name = inputName.getText().toString().trim();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputConfirmPassword.getText().toString();


        if (name.isEmpty()) {
           inputName.setError("Enter FullName!");

        }else if(!email.matches(emailPattern)){
            inputEmail.setError("Enter Correct Email!");
        }
        else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Proper Password.");

        } else if (!password.equals(confirmpassword)) {
            inputConfirmPassword.setError("Password Not Match Both Fields");
        } else {
            progressDialog.setMessage("Please Wait For Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();


                                    progressDialog.dismiss();

                                    Toast.makeText(RegisterActivity.this, "Registratiion successful, please check your email for verification.", Toast.LENGTH_SHORT).show();
                                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                                    Map<String, Object> userInfo = new HashMap<>();


                                    userInfo.put("FullName",name);
                                    userInfo.put("Email",email);
                                    userInfo.put("Password",password);

                                    userInfo.put("isUser","1");
                                    df.set(userInfo);


                                    sendUserToNextActivity();
                                } else {
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }

    private void sendUserToNextActivity() {

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}