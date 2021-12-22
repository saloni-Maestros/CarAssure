package com.kuldeep.carassure.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Activity.HomeActivity;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {
    ImageView back;
    EditText et_currentpass, et_newpass, et_confirmpass;
    TextView txtEmail,txtName,txtMobile;
    MaterialButton mbtn_changepass;
    String UserId = "",DisplayName="",EmailAddress="";
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        back = view.findViewById(R.id.back);
        mbtn_changepass = view.findViewById(R.id.mbtn_changepass);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtName = view.findViewById(R.id.txtName);
        txtMobile = view.findViewById(R.id.txtMobile);
        et_currentpass = view.findViewById(R.id.et_currentpass);
        et_newpass = view.findViewById(R.id.et_newpass);
        progressBar = view.findViewById(R.id.progressBar);
        et_confirmpass = view.findViewById(R.id.et_confirmpass);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HomeActivity.class));

            }
        });
        UserId = SharedHelper.getKey(getActivity(), APPCONSTANT.user_Id);
        Log.e("userid", UserId);
        DisplayName = SharedHelper.getKey(getActivity(), APPCONSTANT.DISPLAY_NAME);
        txtName.setText(DisplayName);
        Log.e("dsadfsda", DisplayName );
        EmailAddress = SharedHelper.getKey(getActivity(), APPCONSTANT.EMAIL_ID);
        txtEmail.setText(EmailAddress);
        Log.e("dsadfsda", EmailAddress );
        mbtn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_currentpass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "please enter current password", Toast.LENGTH_SHORT).show();
                } else if (et_newpass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "please enter new password", Toast.LENGTH_SHORT).show();
                } else if (et_confirmpass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "please enter confirm password", Toast.LENGTH_SHORT).show();
                } else {
                    profileDetails();

                }
            }
        });

        return view;
    }

    public void profileDetails() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.profileDetails)
                .addBodyParameter("id", UserId)
                .addBodyParameter("email", txtEmail.getText().toString().trim())
                .addBodyParameter("name", txtName.getText().toString().trim())
                .addBodyParameter("phone", txtMobile.getText().toString().trim())
                .addBodyParameter("current_password", et_currentpass.getText().toString().trim())
                .addBodyParameter("new_password", et_newpass.getText().toString().trim())
                .addBodyParameter("confirm_password", et_confirmpass.getText().toString().trim())
                .setTag("profileDetails")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dhndklxd", "onResponse: " + response.toString());
                        {
                            try {
                                if (response.getString("result").equals("true")) {
                                    Toast.makeText(getContext(), "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                                    JSONObject jsonObject = new JSONObject(response.getString("data"));

                                } else {

                                    Toast.makeText(getContext(), "" + response.getString("result"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Log.e("fbgfvcf", e.getMessage());

                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("sdsad", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }



}