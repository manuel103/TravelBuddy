package com.example.travelbuddy;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName, etEmail, etPassword;
    Button btnRegister;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout rlayout;
    private Animation animation;


    //private TextInputLayout etEmail;
    //private TextInputLayout textInputUsername;
    //private TextInputLayout etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // make the activity full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        //if user is already logged, just take them to their profile
        if(firebaseAuth.getCurrentUser() != null){
            // profile activity

            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }
        progressDialog = new ProgressDialog(this);

        // Hide the action bar
        getSupportActionBar().hide();


        btnRegister = findViewById(R.id.btn_register);
        etName = findViewById(R.id.textInputUsername);
        etEmail = findViewById(R.id.et_reg_email);
        etPassword = findViewById(R.id.et_reg_password);
        textViewSignin = findViewById(R.id.textViewSignin);

        btnRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        //set animations for scene switching
        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
    }

    public void registerUser(){

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String username = etName.getText().toString().trim();

        // Check if the entered strings are empty or not
        if(TextUtils.isEmpty(email)){

            //if email is empty

            etEmail.setError("Field can't be empty");

            //Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();

            // preventing function from executing any further
            return;

        } else {
            etEmail.setError(null);
            //return;
        }

        if(TextUtils.isEmpty(password)){

            //if password is empty

            etPassword.setError("Field can't be empty");
            //Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }else{
            etPassword.setError(null);
        }

        if(TextUtils.isEmpty(username)){

            etName.setError("Field can't be empty");
            //if password is empty
            //Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }else if (username.length() > 15){
            etName.setError("Username too long");
            return;
        }else {
            etName.setError(null);
        }


        //if validations are ok...First Show progress dialog!
        progressDialog.setMessage("Processing please wait ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // if user is successfully registered and logged in
                            // @ Magnus, start the profile from here

                           finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        }else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed! Please Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == btnRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //Opening Login Activity after registration
            startActivity(new Intent(this, Login.class));

        }
    }

}
