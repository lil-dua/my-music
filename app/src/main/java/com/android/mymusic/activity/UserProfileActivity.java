package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.mymusic.R;
import com.android.mymusic.databinding.ActivityUserProfileBinding;

public class UserProfileActivity extends AppCompatActivity {

    private ActivityUserProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Actions
        Actions();
    }

    private void Actions() {
        //on back
        binding.imageBack.setOnClickListener(view ->{onBackPressed();});
        //log out
        binding.buttonLogout.setOnClickListener(view -> {startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));});
    }
}