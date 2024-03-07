package com.hit.shoppingmanagementapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.adapters.AuthViewPagerAdapter;

public class AuthActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private AuthViewPagerAdapter mViewPagerAdapter;
    private static final String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create the activity and set its content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Initialize member variables
        initViewPagerAndItsAdapter();
        initTabLayoutAndHandleTabSelection();
    }

    private void initViewPagerAndItsAdapter() {
        // Initialize the view pager and its adapter and set the adapter to the view pager
        mViewPager2 = findViewById(R.id.authViewPager);
        mViewPagerAdapter = new AuthViewPagerAdapter(this);
        mViewPager2.setAdapter(mViewPagerAdapter);
    }

    private void initTabLayoutAndHandleTabSelection() {
        // Initialize the tab layout and handle tab selection
        mTabLayout = findViewById(R.id.authTabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            // Do nothing
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
    }
}