package com.example.grubexpress.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import com.example.grubexpress.R;
import com.example.grubexpress.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
    }

    private void setVariable() {
        Button b1 = findViewById(R.id.SignUpbtn);
        EditText name = findViewById(R.id.editTextText);
        EditText Email = findViewById(R.id.editTextTextEmailAddress2);
        EditText Pass = findViewById(R.id.editTextTextPassword);
        EditText confirmPass = findViewById(R.id.editTextTextPassword2);
        ProgressBar pBar = findViewById(R.id.progressBar);
        binding.SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBar.setVisibility(View.VISIBLE);
                String username = name.getText().toString();
                String userEmail = Email.getText().toString();
                String userPassword = Pass.getText().toString();
                String confirmPassword = confirmPass.getText().toString();

                if (userPassword.length() < 8) {
                    pBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Password must be at least 8 characters long.", Toast.LENGTH_SHORT).show();
                } else if (!userPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$")) {
                    pBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Password must contain at least one letter, one number, and one special character.", Toast.LENGTH_SHORT).show();
                } else if (!userPassword.equals(confirmPassword)) {
                    pBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pBar.setVisibility(View.GONE);
                                    Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
