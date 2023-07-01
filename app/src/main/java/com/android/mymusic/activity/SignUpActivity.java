package com.android.mymusic.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.mymusic.R;
import com.android.mymusic.databinding.ActivitySignUpBinding;
import com.android.mymusic.model.ApiRespone;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String encodeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Actions
        Actions();
    }

    private void Actions() {
        //sign up
        binding.buttonSignUp.setOnClickListener(view -> {
            if(isValidSignUpDetails()){
                signUp();
            }
        });
        // Image Profile
        binding.imageProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
        //back to login
        binding.textSignIn.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    private void signUp() {
        loading(true);

        //logic sign up below
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();
        String username = binding.inputName.getText().toString().trim();
        String phone = binding.inputPhone.getText().toString().trim();
        String image = encodeImage;

        Dataservice dataservice = APIService.getService();
        Call<ApiRespone> callback = dataservice.PerformUserSignUp(email,password,username,phone,image);
        callback.enqueue(new Callback<ApiRespone>() {
            @Override
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.code()==200){
                    if(response.body().getStatus().equals("ok")){
                        if(response.body().getResultCode()==1){
                            loading(false);
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.putExtra("email",email);
                            startActivity(intent);
                            finish();
                        }else {
                            loading(false);
                            showToast("User already exists....");
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
    // this function will encode an image you choose in gallery.
    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight,false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // Pick image function
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    if(result.getData() != null){
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imageProfile.setImageBitmap(bitmap);
                            binding.textAddImage.setVisibility(View.GONE);
                            encodeImage = encodeImage(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    // Check valid sign up
    private Boolean isValidSignUpDetails(){
        if(encodeImage == null){
            showToast("Select your image");
            return false;
        }else if(binding.inputName.getText().toString().trim().isEmpty()){
            showToast("Enter name");
            binding.inputName.setError("User name is required!");
            binding.inputName.requestFocus();
            return false;
        }else if(binding.inputEmail.getText().toString().trim().isEmpty()){
            binding.inputName.setError("Email is required!");
            binding.inputName.requestFocus();
            showToast("Enter email");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            showToast("Enter valid email");
            binding.inputName.setError("Enter valid email!");
            binding.inputName.requestFocus();
            return false;
        }else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            binding.inputName.setError("Password is required!");
            binding.inputName.requestFocus();
            return false;
        }else if(binding.inputConfirmPassword.getText().toString().trim().isEmpty()){
            showToast("Confirm password");
            binding.inputName.setError("Confirm password is required!");
            binding.inputName.requestFocus();
            return false;
        }else if(!binding.inputPassword.getText().toString()
                .equals(binding.inputConfirmPassword.getText().toString())){
            showToast("Password and Confirm password must be same");
            return false;
        }
        // if all information was valid and correct request.
        return true;
    }

    // loading for Sign Up
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignUp.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignUp.setVisibility(View.VISIBLE);
        }
    }
}