package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Responsible for the TabPaging between Map and YourInscriptions.
 */
public class TabManagerFragment extends Fragment {
    private TabPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private static final String TAG = "TabManagerFragment";

    public static TabManagerFragment newInstance() {
        return new TabManagerFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toolbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pagerAdapter = new TabPagerAdapter(getChildFragmentManager(), getContext());
        viewPager = view.findViewById(R.id.toolbar_viewpager);
        viewPager.setAdapter(pagerAdapter);
        Log.d(TAG, "onViewCreated: TabManager initialized.");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
