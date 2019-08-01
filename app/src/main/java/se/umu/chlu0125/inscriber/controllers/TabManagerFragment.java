package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.umu.chlu0125.inscriber.R;

public class TabManagerFragment extends Fragment {
    TabPagerAdapter pagerAdapter;
    ViewPager viewPager;


    public static TabManagerFragment newInstance(){
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
