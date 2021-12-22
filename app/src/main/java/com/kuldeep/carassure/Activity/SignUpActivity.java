package com.kuldeep.carassure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Model.UserTypeModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUpActivity extends Activity {
    TextView text_login;
    MaterialButton mt_submit;
    Spinner spin;
    ImageView showpassword;
    EditText edt_username,edit_displayname,edit_email,edt_pwd;
    CheckBox checkbox;
    ProgressBar progressBar;

    ArrayList<UserTypeModel> userTypeModelArrayList;
    ArrayList<String> Arr_usertypeID;
    ArrayList<String> Arr_usertypeName;

    String UserTypeId="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        text_login=findViewById(R.id.text_login);
        mt_submit=findViewById(R.id.mt_submit);
        spin=findViewById(R.id.spin);
        edt_username=findViewById(R.id.edt_username);
        edit_displayname=findViewById(R.id.edit_displayname);
        edit_email=findViewById(R.id.edit_email);
        edt_pwd=findViewById(R.id.edt_pwd);
        checkbox=findViewById(R.id.checkbox);
        showpassword=findViewById(R.id.showpassword);
        progressBar=findViewById(R.id.progressBar);

        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        usertype();
        mt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();

                //startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                //finish();
            }
        });
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            finish();
            }
        });
    }
    public void usertype(){
        AndroidNetworking.post(Api.user_type)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("fdskjgl", response.toString());
                        userTypeModelArrayList= new ArrayList<>();
                        Log.e("uygyggv", "onRespons " + response.toString());
                        Arr_usertypeID = new ArrayList<>();
                        Arr_usertypeName = new ArrayList<>();
                        Arr_usertypeID.add("0");
                        Arr_usertypeName.add("Select");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Arr_usertypeID.add(jsonObject.getString("id"));
                                Arr_usertypeName.add(jsonObject.getString("user_type"));
                            }

                            ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Arr_usertypeName);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin.setAdapter(aa);

                            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String usertypeId = Arr_usertypeID.get(position);
                                    ((TextView) spin.getChildAt(0)).setTextColor(Color.BLACK);
                                    ((TextView) spin.getChildAt(0)).setTextSize(15);

                                    if (usertypeId.equals("0")){

                                    }else {

                                        UserTypeId=usertypeId;
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void signup(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.signup)
                .addBodyParameter("user_type",UserTypeId)
                .addBodyParameter("username",edt_username.getText().toString().trim())
                .addBodyParameter("display_name",edit_displayname.getText().toString().trim())
                .addBodyParameter("email",edit_email.getText().toString().trim())
                .addBodyParameter("password",edt_pwd.getText().toString().trim())
                .setPriority(Priority.HIGH)
                .setTag("signup")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dsjhkjghks",response.toString());
                        try {
                            if (response.getString("result").equals("Registration Successfull")){
                                Toast.makeText(SignUpActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                SharedHelper.putkey(SignUpActivity.this, APPCONSTANT.user_Id,response.getString("id"));
                                SharedHelper.putkey(SignUpActivity.this, APPCONSTANT.DISPLAY_NAME,response.getString("display_name"));
                                SharedHelper.putkey(SignUpActivity.this, APPCONSTANT.EMAIL_ID,response.getString("email"));

                                Log.e("dfgdfdf", response.getString("display_name") );
                                Log.e("dfgdfdf", response.getString("email"));

                            }else {

                                Toast.makeText(SignUpActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("djkhgf", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("sflkhl", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }
}