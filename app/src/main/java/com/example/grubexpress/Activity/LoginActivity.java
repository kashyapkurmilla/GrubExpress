package com.example.grubexpress.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grubexpress.R;
import com.example.grubexpress.databinding.ActivityIntroBinding;
import com.example.grubexpress.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends BaseActivity{
ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText userEmail = findViewById(R.id.editTextTextEmailAddress);
        EditText userPassword = findViewById(R.id.editTextTextPassword2);
        ProgressBar pBar = findViewById(R.id.progressBar2);

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBar.setVisibility(View.VISIBLE);
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    // Display toast if email field is empty
                    Toast.makeText(LoginActivity.this, "Enter Email ID", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    // Display toast if password field is empty
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Authentication Successful.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        pBar.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}