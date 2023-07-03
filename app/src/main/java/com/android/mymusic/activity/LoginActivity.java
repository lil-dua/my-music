package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.mymusic.R;
import com.android.mymusic.databinding.ActivityLoginBinding;
import com.android.mymusic.model.ApiRespone;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Action
        Action();

    }

    private void Action() {
        //Sign in button
        binding.buttonSignIn.setOnClickListener(view -> {
            if(isValidLoginDetails()){
                signIn();
            }
        });
        //Sign up button
        binding.textCreateNewAccount.setOnClickListener(view -> startActivity(new Intent(this, SignUpActivity.class)));
    }

    private void signIn() {
        loading(true);
        //logic sign up below
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        Dataservice dataservice = APIService.getService();
        Call<ApiRespone> callback = dataservice.PerformUserLogin(email,password);
        callback.enqueue(new Callback<ApiRespone>() {
            @Override
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.code()==200){
                    if(response.body().getStatus().equals("ok")){
                        if(response.body().getResultCode()==1){
                            String email = response.body().getEmail();
                            loading(false);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("email",email);
                            startActivity(intent);
                            finish();
                        }else {
                            loading(false);
                            binding.inputPassword.setError("Email or password was wrong!");
                            binding.inputPassword.requestFocus();
                            showToast("Email or password was wrong....");
                        }
                    }else {
                        loading(false);
                        showToast("Something went wrong...");
                    }
                }else{
                    loading(false);
                    showToast("Something went wrong...");
                }
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {

            }
        });

    }


    //show Toast
    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    // Check valid sign in
    private Boolean isValidLoginDetails(){
        if(binding.inputEmail.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            binding.inputEmail.setError("Email is required!");
            binding.inputEmail.requestFocus();
            return false;
        }else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            binding.inputEmail.setError("Password is required!");
            binding.inputEmail.requestFocus();
            return false;
        }
        // if all information was valid and correct request.
        return true;
    }
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.buttonSignIn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}