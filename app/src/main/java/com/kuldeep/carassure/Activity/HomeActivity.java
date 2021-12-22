package com.kuldeep.carassure.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.kuldeep.carassure.Fragment.BidFragment;
import com.kuldeep.carassure.Fragment.FavouriteCarFragment;
import com.kuldeep.carassure.Fragment.HomescreenFragment;
import com.kuldeep.carassure.Fragment.LatestFragment;
import com.kuldeep.carassure.Fragment.ProfileFragment;
import com.kuldeep.carassure.Fragment.SellFragment;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.SharedHelper;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    public static DrawerLayout drawerlayout;
    NavigationView navigationView;
TabLayout tablayout1;
    LinearLayout ll_call,ll_Conditions,ll_policies,ll_logout;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerlayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_view);
        tablayout1 = findViewById(R.id.tablayout1);

        ll_call = findViewById(R.id.ll_call);
        ll_Conditions = findViewById(R.id.ll_Conditions);
        ll_policies = findViewById(R.id.ll_policies);
        ll_logout = findViewById(R.id.ll_logout);

        ll_call.setOnClickListener(this);
        ll_Conditions.setOnClickListener(this);
        ll_policies.setOnClickListener(this);
        ll_logout.setOnClickListener(this);

        bottomNavigation=findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(this);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.item_container, new HomescreenFragment()).addToBackStack(null).commit();

        }
        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(HomeActivity.this);
                }
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Are you sure you want to logout in the app")
                        .setPositiveButton(Html.fromHtml("<font color='#E50000'>Ok</font>"), new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(final DialogInterface dialog, int which) {
                                SharedHelper.putkey(HomeActivity.this, APPCONSTANT.user_Id,"");
                                Intent intent = new Intent(HomeActivity.this, SplashScreenActivity.class);
                                if(Build.VERSION.SDK_INT >= 11) {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                } else {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#E50000'>Cancel</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                       // .setIcon(R.drawable.ic_twotone_power_settings_new_24)
                        .show();
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_call:
                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                drawerlayout.closeDrawer(GravityCompat.START);
                break;

                case R.id.ll_Conditions:
                startActivity(new Intent(HomeActivity.this, TermsConditionActivity.class));
                drawerlayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_policies:
                startActivity(new Intent(HomeActivity.this,PoliciesActivity.class));
                drawerlayout.closeDrawer(GravityCompat.START);
                break;
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_bid:
                getSupportFragmentManager().beginTransaction().replace(R.id.item_container, new BidFragment()).commit();
                break;
            case R.id.nav_sell:
                getSupportFragmentManager().beginTransaction().replace(R.id.item_container, new SellFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.item_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.item_container, new FavouriteCarFragment()).commit();
                break;
        }
        return true;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}