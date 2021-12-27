package com.kuldeep.carassure.Activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.Api;

import org.json.JSONObject;

public class ForgotPasswordActivity extends Activity {
    MaterialButton btn_reset;
    EditText edt_email1;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        progressBar = findViewById(R.id.progressBar);

        btn_reset = (MaterialButton) findViewById(R.id.btn_reset);
        edt_email1 = (EditText) findViewById(R.id.edt_email1);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_email1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "please enter email", Toast.LENGTH_SHORT).show();
                } else {
                    forget_password();
                }
            }
        });


      /*  btn_reset.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view)
                    {
                        Log.v("EditText", edt_email1.getText().toString());
                        Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                });*/

    }

    public void forget_password() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.forget_password)
                .addBodyParameter("email", edt_email1.getText().toString().trim())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("jvkxmvkv ", "onResponse: " + response.toString());
                        try {
                            if (response.getString("message").equals("Mail Sent Successfully")) {
                                Toast.makeText(ForgotPasswordActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("fckdkck", "onResponse: " + e.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("cjhncncm", "onError: " + anError);
                        progressBar.setVisibility(View.GONE);

                    }
                });


    }
}