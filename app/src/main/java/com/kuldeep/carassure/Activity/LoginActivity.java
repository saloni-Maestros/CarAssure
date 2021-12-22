package com.kuldeep.carassure.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONObject;

import javax.security.auth.login.LoginException;

public class LoginActivity extends Activity {

    TextView text_forgot,signup;
    EditText edit_email,edt_pwd,edt_uname;
    MaterialButton summit_btn;
    ImageView showpassword;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text_forgot=findViewById(R.id.text_forgot);
        signup=findViewById(R.id.signup);
        edit_email=findViewById(R.id.edit_email);
        edt_uname=findViewById(R.id.edt_uname);
        edt_pwd=findViewById(R.id.edt_pwd);
        showpassword=findViewById(R.id.showpassword);
        summit_btn=findViewById(R.id.summit_btn);
        progressBar=findViewById(R.id.progressBar);


        summit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });



        text_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));

            }
        });

    }

    public void login(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.login)
                .addBodyParameter("username",edt_uname.getText().toString().trim())
                .addBodyParameter("email",edit_email.getText().toString().trim())
                .addBodyParameter("password",edt_pwd.getText().toString().trim())
                .setPriority(Priority.HIGH)
                .setTag("login")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("sdgkjhgk", response.toString());
                        try {
                            if (response.getString("result").equals("Login Successfull")){
                                Toast.makeText(LoginActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                finish();
                                SharedHelper.putkey(LoginActivity.this, APPCONSTANT.user_Id,response.getString("id"));

                            }else {

                                Toast.makeText(LoginActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("dhfkjhd", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("kuhggs", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}