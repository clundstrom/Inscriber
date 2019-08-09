package se.umu.chlu0125.inscriber.controllers;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Pages between Fragments.
 */
public class TabPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    Context mContext;
    private static final String TAG = "TabPagerAdapter";


    public TabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MapFragment.getInstance();
            case 1:
                return InscriptionListFragment.getInstance();
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        InscriptionListFragment.getInstance().updateUI();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}