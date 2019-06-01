package com.example.travelbuddy;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_home2_page);


        firebaseAuth = FirebaseAuth.getInstance();

        //if user is already logged in...
        if(firebaseAuth.getCurrentUser() != null){
            // profile activity

            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        forgotPass = (TextView) findViewById(R.id.tvForgot);

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        forgotPass.setOnClickListener(this);

}

    public void userLogin(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check if the entered strings are empty or not
        if(TextUtils.isEmpty(email)){

            //if email is empty
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();

            // kuzuia function from executing any further
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
            userLogin();
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
