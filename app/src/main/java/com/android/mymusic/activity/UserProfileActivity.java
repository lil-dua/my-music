package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import com.android.mymusic.R;
import com.android.mymusic.databinding.ActivityUserProfileBinding;
import com.android.mymusic.model.User;
import com.android.mymusic.service.APIRetrofitClient;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private ActivityUserProfileBinding binding;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        email = getIntent().getStringExtra("email");
        email = "thanhthuy2604@gmail.com";
        //get and set data user
        getDataUser(email);
        //Actions
        actions();
    }

    private void getDataUser(String email) {
        Dataservice dataservice = APIService.getService();
        Call<List<User>> callback = dataservice.GetUser(email);
       callback.enqueue(new Callback<List<User>>() {
           @Override
           public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               ArrayList<User> users = (ArrayList<User>) response.body();
               User user = users.get(0);
               binding.textViewName.setText(user.getNameUser());
               binding.textViewEmail.setText(user.getEmailUser());
               binding.textViewPhone.setText(user.getPhoneUser());
               binding.imgUser.setImageBitmap(decodeImage(user.getImageUser()));
               Toast.makeText(UserProfileActivity.this, "Ok", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(Call<List<User>> call, Throwable t) {
               Toast.makeText(UserProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void actions() {
        //on back
        binding.imageBack.setOnClickListener(view ->{onBackPressed();});
        //log out
        binding.buttonLogout.setOnClickListener(view -> {startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));});
    }

    private Bitmap decodeImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}