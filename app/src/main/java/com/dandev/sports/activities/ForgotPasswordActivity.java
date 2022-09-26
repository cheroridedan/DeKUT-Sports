package com.dandev.sports.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dandev.sports.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {


    Button resetEmailPassword;
    EditText emailPasswordReset;

    ProgressDialog progressDialog;

    FirebaseAuth mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);






        emailPasswordReset=findViewById(R.id.emailPasswordReset);
        resetEmailPassword=findViewById(R.id.resetEmailPassword);
        progressDialog=new ProgressDialog(this);

        resetEmailPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailPasswordReset.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your registered email", Toast.LENGTH_SHORT).show();
                    emailPasswordReset.setError("Email is required");
                    emailPasswordReset.requestFocus();
                }else  if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    emailPasswordReset.setError("Valid email is required");
                    emailPasswordReset.requestFocus();
                }else
                {
                    progressDialog.show();
                    resetPassword(email);
                }
            }
        });



    }

    private void resetPassword(String email) {

        mUser = FirebaseAuth.getInstance();
        mUser.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(ForgotPasswordActivity.this, LoginActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();



            }
        });

    }
}