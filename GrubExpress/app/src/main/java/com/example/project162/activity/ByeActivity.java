package com.example.project162.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project162.R;
import com.example.project162.databinding.ActivityByeBinding;

public class ByeActivity extends AppCompatActivity {
    ActivityByeBinding binding;
    private Button submitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye);
        submitbtn = findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ByeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}