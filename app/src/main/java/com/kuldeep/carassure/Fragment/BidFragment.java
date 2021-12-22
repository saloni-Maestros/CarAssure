package com.kuldeep.carassure.Fragment;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.kuldeep.carassure.Activity.HomeActivity;
import com.kuldeep.carassure.Adapter.HomeFragmentAdapter;
import com.kuldeep.carassure.R;


public class BidFragment extends Fragment {
    TabLayout tablayout1;
    ViewPager viewPager;
    ImageView img_menu;
    MaterialButton btn_placebid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bid, container, false);

        img_menu=view.findViewById(R.id.img_menu);
        tablayout1=view.findViewById(R.id.tablayout1);
        viewPager = view. findViewById(R.id.viewPager);
        btn_placebid = view.findViewById(R.id.btn_placebid);

      /*  final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tablayout1);
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getFragmentManager());
        adapter.addFragment(BlogFragment.newInstance(), "One");
        adapter.addFragment(TrendingFragment.newInstance(), "Two");

        viewPager.setAdapter(adapter);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });*/


      //  Fragment fragment = getActivity().supportFragmentManager.findFragmentByTag("android:switcher:" + viewPager.getId() + ":" + fragmentPosition);


        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout1.setupWithViewPager(viewPager);
      /*  viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout1));*/

        tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
      /*  tablayout1.addTab(tablayout1.newTab().setText("LATEST"));
        tablayout1.addTab(tablayout1.newTab().setText("MY AUCTIONS"));
        tablayout1.addTab(tablayout1.newTab().setText("MY PURCHING"));
        tablayout1.setTabGravity(TabLayout.GRAVITY_FILL);*/


      /*  btn_placebid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CarDeatilsActivity.class);
                startActivity(intent);
            }
        });
*/
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.drawerlayout.openDrawer(GravityCompat.START);
            }
        });
        // onBack(view);

    return view;
    }
}