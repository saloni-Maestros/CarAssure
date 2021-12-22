package com.kuldeep.carassure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.R;

public class SplashsecondActivity extends Activity {

    MaterialButton btn_start;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashsecond);

        btn_start=findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SplashsecondActivity.this,LoginActivity.class));
                finish();

            }
        });
    }
}