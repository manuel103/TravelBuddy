package com.example.travelbuddy.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelbuddy.R;
import com.example.travelbuddy.RegisterActivity;
import com.example.travelbuddy.api.RetrofitClient;
import com.example.travelbuddy.models.LoginResponse;
import com.example.travelbuddy.storage.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    TextView tvRegister;
    Button btnLogin;
    TextView forgotPass;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the action bar
        getSupportActionBar().hide();

        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //if user is already logged in...
        if(firebaseAuth.getCurrentUser() != null){
            // profile activity

            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        forgotPass = findViewById(R.id.tvForgot);

        progressDialog = new ProgressDialog(this);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        forgotPass.setOnClickListener(this);

}

    public void userLogin(){

        //LoginActivity code
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password should be at least 6 characters long");
            etPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();


                if (!loginResponse.isError()) {

                    //proceed to login
                    SharedPrefManager.getInstance(LoginActivity.this)
                            .saveUser(loginResponse.getUser());

                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    public void Login(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check if the entered strings are empty or not
        if(TextUtils.isEmpty(email)){

            //if email is empty
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();

            return;
        }
        if(TextUtils.isEmpty(password)){

            //if password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        //if validations are ok...First Show progress dialog!
        progressDialog.setMessage("Processing please wait ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin){
            Login();
        }

        if(view == tvRegister){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }

        if(view == forgotPass){
            finish();
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        }

    }

}
