package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.umu.chlu0125.inscriber.R;

public class TabManagerFragment extends Fragment {
    TabPagerAdapter pagerAdapter;
    ViewPager viewPager;


    public static TabManagerFragment newInstance(){
        // bundles and args here..

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
    }
}
