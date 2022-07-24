package com.ra.nursebot.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ra.nursebot.R;
import com.ra.nursebot.databinding.ActivityStartBinding;
import com.ra.nursebot.ui.MainActivity;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v ->
                startActivity(new Intent(StartActivity.this, MainActivity.class)));
    }
}