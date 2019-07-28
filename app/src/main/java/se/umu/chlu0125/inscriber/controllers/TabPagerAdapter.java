package se.umu.chlu0125.inscriber.controllers;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.gms.maps.SupportMapFragment;

import se.umu.chlu0125.inscriber.R;

public class TabPagerAdapter extends FragmentPagerAdapter {

    Context mContext;


    public TabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MapFragment();
            case 1:
                return new GuideDialogFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.map_activity_title);
            case 1:
                return mContext.getString(R.string.second_tab_title);
            default:
                return null;
        }

    }
}