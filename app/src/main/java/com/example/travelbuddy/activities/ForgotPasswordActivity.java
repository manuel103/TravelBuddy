package com.example.travelbuddy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ProgressBar progressBar;
    EditText userEmail;
    Button userPass;
    private TextView textViewSignin;

    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Hide the action bar
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbar = findViewById(R.id.toolbar3);
        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.et_ChangePass);
        userPass = findViewById(R.id.forgotPass);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        textViewSignin.setOnClickListener(this);

        //toolbar.setTitle("Forgot Password");
        firebaseAuth = FirebaseAuth.getInstance();



        userPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String email = userEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){

                    //if email is empty
                    Toast.makeText(ForgotPasswordActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Password sent to your email", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(ForgotPasswordActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == textViewSignin){
            //Opening LoginActivity Activity after registration
            startActivity(new Intent(this, LoginActivity.class));

        }
    }
}
