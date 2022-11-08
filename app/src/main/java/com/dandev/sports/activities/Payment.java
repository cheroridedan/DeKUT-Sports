package com.dandev.sports.activities;

import static com.dandev.sports.AppConstants.BUSINESS_SHORT_CODE;
import static com.dandev.sports.AppConstants.CALLBACKURL;
import static com.dandev.sports.AppConstants.PARTYB;
import static com.dandev.sports.AppConstants.PASSKEY;
import static com.dandev.sports.AppConstants.TRANSACTION_TYPE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dandev.sports.R;
import com.dandev.sports.AppUtils.Utils;
import com.dandev.sports.model.AccessToken;
import com.dandev.sports.model.STKPush;
import com.dandev.sports.Services.DarajaApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Payment extends AppCompatActivity implements View.OnClickListener {


    public static final String college = "college";
    public static final String email = "email";
    public static final String gender = "gender";
    public static final String studentid = "studentid";
    public static final String course = "course";
    public static final String fname = "fname";
    public static final String lname = "lname";
    public static final String year = "year";
    public static final String passport = "passport";
    public static final String ntnId = "ntnId";
    public static final String SchId = "SchId";
    public static final String chiefLetter = "chiefLetter";
    public static final String uniLetter = "uniLetter";

    private DarajaApiClient mApiClient;
    private ProgressDialog mProgressDialog;
    private String collegee, emaill, genderr, studentidd, coursee, fnamee, lnamee, yearr, passportt, ntnIdd, SchIdd, chiefLetterr, uniLetterr;

    DatabaseReference databaseReference;

    @BindView(R.id.etAmount)
    EditText mAmount;
    @BindView(R.id.etPhone)EditText mPhone;
    @BindView(R.id.btnPay)
    Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(this);
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.

        mPay.setOnClickListener(this);

        getAccessToken();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        Intent i = getIntent();
        collegee = i.getStringExtra(college);
        emaill = i.getStringExtra(email);
        genderr = i.getStringExtra(gender);
        //studentidd = i.getStringExtra(studentid);
        coursee = i.getStringExtra(course);
        fnamee = i.getStringExtra(fname);
        lnamee = i.getStringExtra(lname);
        yearr = i.getStringExtra(year);
        passportt = i.getStringExtra(passport);
        ntnIdd = i.getStringExtra(ntnId);
        SchIdd = i.getStringExtra(SchId);
        chiefLetterr = i.getStringExtra(chiefLetter);
        uniLetterr = i.getStringExtra(uniLetter);

    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view== mPay){
            String phone_number = mPhone.getText().toString();
            String amount = mAmount.getText().toString();


            String id = databaseReference.push().getKey();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("Passport", passportt);
            hashMap.put("fName", fnamee);
            hashMap.put("lName", lnamee);
            hashMap.put("gender", genderr);
            hashMap.put("Sponsor_no", phone_number);
            hashMap.put("Amount", amount);
            hashMap.put("college",collegee);
            hashMap.put("course", coursee);
            hashMap.put("email", emaill);
            hashMap.put("year", yearr);
            hashMap.put("National_id",ntnIdd);
            hashMap.put("School_id", SchIdd);
            hashMap.put("University_letter",uniLetterr);
            hashMap.put("chief_letter", chiefLetterr);

            databaseReference.child("Approve payments").child(id).updateChildren(hashMap)
                    .addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               // Toast.makeText(payment.this, "Decline successful", Toast.LENGTH_SHORT).show();
                                performSTKPush(phone_number,amount);

                            }
                        }
                    }
            ).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Payment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            //String id= databaseReference.push().getKey();

        }
    }

    private void deleteStudent(String id) {
        DatabaseReference drStudent = FirebaseDatabase.getInstance().getReference("Student").child(studentid);
        drStudent.removeValue();

    }


    public void performSTKPush(String phone_number,String amount) {

        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "MPESA Android Test", //Account reference
                "Testing"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response.body());
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}