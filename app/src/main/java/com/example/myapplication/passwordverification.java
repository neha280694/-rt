package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class passwordverification extends AppCompatActivity {
    private EditText editTextPhoneNumber;
    private Button buttonSendOTP;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordverification);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonSendOTP = findViewById(R.id.buttonSendOTP);
        mAuth = FirebaseAuth.getInstance();

        buttonSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+91" + editTextPhoneNumber.getText().toString().trim(); // Replace "+1" with the appropriate country code
                if (TextUtils.isEmpty(phoneNumber)) {
                    editTextPhoneNumber.setError("Enter a valid phone number");
                } else {
                    if (isValidPhoneNumber(phoneNumber)) {
                        Intent intent = new Intent(passwordverification.this, otpconfirm.class);
                        intent.putExtra("phoneNumber", phoneNumber);
                        startActivity(intent);
                    } else {
                        editTextPhoneNumber.setError("Invalid phone number format");
                    }
                }
            }
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Implement your phone number validation here.
        // For example, you can use regular expressions to validate the format.
        // Return true if the phone number is valid; otherwise, return false.
        return phoneNumber.matches("^\\+[0-9]{1,15}$"); // E.164 format: +[country code][subscriber number]
    }
}