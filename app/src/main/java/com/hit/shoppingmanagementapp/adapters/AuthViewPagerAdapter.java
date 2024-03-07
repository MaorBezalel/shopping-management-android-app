package com.hit.shoppingmanagementapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hit.shoppingmanagementapp.fragments.LoginTabFragment;
import com.hit.shoppingmanagementapp.fragments.SignupTabFragment;

public class AuthViewPagerAdapter extends FragmentStateAdapter {
    public AuthViewPagerAdapter(@NonNull FragmentActivity iFragmentActivity) {
        super(iFragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int iPosition) {
        switch (iPosition) {
            case 0: return new LoginTabFragment();
            case 1: return new SignupTabFragment();
            default:return new LoginTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
