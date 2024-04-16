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

import com.example.grubexpress.Domain.User;
import com.example.grubexpress.R;
import com.example.grubexpress.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends BaseActivity {

    ActivitySignupBinding binding;
    String userName , userEmail , phoneNumber ;
    int GrubCoins ;
    DatabaseReference myRef ;
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
                    // Check email uniqueness
                    mAuth.fetchSignInMethodsForEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        SignInMethodQueryResult result = task.getResult();
                                        if (result != null && result.getSignInMethods() != null && result.getSignInMethods().isEmpty()) {
                                            // Email is unique, proceed with registration
                                            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            pBar.setVisibility(View.GONE);
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                pushValuestoDatabase();
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
                                        } else {
                                            // Email already exists
                                            pBar.setVisibility(View.GONE);
                                            Toast.makeText(SignupActivity.this, "This email is already registered.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Task failed
                                        pBar.setVisibility(View.GONE);
                                        Toast.makeText(SignupActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void pushValuestoDatabase() {
        userName = binding.editTextText.getText().toString();
        userEmail = binding.editTextTextEmailAddress2.getText().toString();
        phoneNumber = binding.editTextPhone2.getText().toString();
        GrubCoins = 50;

        User user = new User(userName, userEmail, phoneNumber, GrubCoins);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users"); // Assuming "Users" is the root node for user data

        // Get the current user's UID
        String uid = mAuth.getInstance().getCurrentUser().getUid();

        // Use UID as the key for the database reference
        myRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Clear input fields upon successful database write
                    binding.editTextText.setText("");
                    binding.editTextTextEmailAddress2.setText("");
                    binding.editTextPhone2.setText("");
                } else {
                    // Handle database write failure
                    Toast.makeText(SignupActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
