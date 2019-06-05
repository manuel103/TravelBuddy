package com.example.travelbuddy;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.travelbuddy.activities.LoginActivity;
import com.example.travelbuddy.api.RetrofitClient;
import com.example.travelbuddy.models.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etName, etEmail, etPassword;
    Button btnRegister;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private RelativeLayout rlayout;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // make the activity full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        // Hide the action bar
        getSupportActionBar().hide();

        btnRegister = findViewById(R.id.btn_register);
        etName = findViewById(R.id.textInputUsername);
        etEmail = findViewById(R.id.et_reg_email);
        etPassword = findViewById(R.id.et_reg_password);
        textViewSignin = findViewById(R.id.textViewSignin);

        //onclick listeners
        btnRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        //set animations for scene switching
        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
    }

    public void registerUser(){

        //Code to register user

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String user_name = etName.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password should be at least 6 characters long");
            etPassword.requestFocus();
            return;
        }

        if (user_name.isEmpty()) {
            etName.setError("Username required");
            etName.requestFocus();
            return;
        }


        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, user_name);


        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(RegisterActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                } else if (response.code() == 422) {
                    Toast.makeText(RegisterActivity.this, "This user already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == btnRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //Opening LoginActivity Activity after registration
            startActivity(new Intent(this, LoginActivity.class));

        }
    }

}
