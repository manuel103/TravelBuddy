package com.example.travelbuddy;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword;
    Button btnRegister;

    // specify the register server file
    final String url_Register = "https://atifnaseem22.000webhostapp.com/register_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // make the activity full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_reg_email);
        etPassword = (EditText) findViewById(R.id.et_reg_password);
        btnRegister = (Button) findViewById(R.id.btn_register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = etName.getText().toString();
                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();

                new RegisterUser().execute(Name, Email, Password);
            }
        });


        // Hide the action bar
        getSupportActionBar().hide();

        // Activate gradient animations in Register Activity

        RelativeLayout relativeLayout = findViewById(R.id.gradient);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }


    public class RegisterUser extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String Name = strings[0];
            String Email = strings[1];
            String Password = strings[2];

            String finalURL = url_Register + "?user_name=" + Name +
                    "&user_id=" + Email +
                    "&user_password=" + Password;

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .get()
                        .build();
                Response response = null;

                try {
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();

                        if (result.equalsIgnoreCase("User registered successfully")) {
                            showToast("Registration Successful!");
                            Intent i = new Intent(RegisterActivity.this,
                                    Login.class);
                            startActivity(i);
                            finish();
                        } else if (result.equalsIgnoreCase("User already exists")) {
                            showToast("User already exists");
                        } else {
                            showToast("oops! please try again");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }


    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
