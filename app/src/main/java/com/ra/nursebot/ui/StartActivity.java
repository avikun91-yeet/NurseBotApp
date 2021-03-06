package com.ra.nursebot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ra.nursebot.data.Repository;
import com.ra.nursebot.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v ->
                startActivity(new Intent(StartActivity.this, MainActivity.class)));

        //Woo... DANGER
        binding.btnNuke.setOnClickListener(v -> Repository.instance().nukeAllTable());
    }
}