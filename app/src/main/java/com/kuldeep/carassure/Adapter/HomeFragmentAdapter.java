package com.kuldeep.carassure.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kuldeep.carassure.Fragment.HomescreenFragment;
import com.kuldeep.carassure.Fragment.LatestFragment;
import com.kuldeep.carassure.Fragment.MyAuctionsFragment;
import com.kuldeep.carassure.Fragment.MyPurchesFragment;

public class HomeFragmentAdapter  extends FragmentPagerAdapter {
    public HomeFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) return new LatestFragment();
        if(position == 1) return new MyAuctionsFragment();
        if(position == 2) return new MyPurchesFragment();

        throw new IllegalStateException("Unexpected position " + position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) return "LATEST";
        if(position == 1) return "MY AUCTIONS";
        if(position == 2) return "MY PURCHING";

        throw new IllegalStateException("Unexpected position " + position);
    }
}


/*extends Fragment {
    private Context myContext;
    int totalTabs;
    public Object HomescreenFragment;

    public HomeFragmentAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LatestFragment homeFragment = new LatestFragment();
                return homeFragment;
            case 1:
                MyAuctionsFragment homeFragment2 = new MyAuctionsFragment();
                return homeFragment2;
            case 2:
                MyPurchesFragment homeFragment3 = new MyPurchesFragment();
                return homeFragment3;

            default:
                return (Fragment) HomescreenFragment;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
*/