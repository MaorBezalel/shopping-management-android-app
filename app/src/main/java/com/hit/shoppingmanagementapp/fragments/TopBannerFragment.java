package com.hit.shoppingmanagementapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.utils.FirebaseUtils;

public class TopBannerFragment extends Fragment {

    private TextView mTopBannerText;

    @Override
    public View onCreateView(@NonNull LayoutInflater iInflater, ViewGroup iContainer,
                             Bundle iSavedInstanceState) {
        return iInflater.inflate(R.layout.fragment_top_banner, iContainer, false);
    }

    @Override
    public void onViewCreated(View iView, Bundle iSavedInstanceState) {
        super.onViewCreated(iView, iSavedInstanceState);

        mTopBannerText = iView.findViewById(R.id.topBannerText);
        if (FirebaseUtils.isUserLoggedIn()) {
            setTopBannerText(username -> mTopBannerText.setText("Welcome, " + username));
        } else {
            mTopBannerText.setText("Welcome to Shopping App");
        }
    }

    private void setTopBannerText(OnUserDetailsListener iListener) {
        FirebaseUtils.getCurrentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    iListener.onUserDetailsReceived(document.getString("username"));
                } else {
                    iListener.onUserDetailsReceived("Unknown user");
                }
            } else {
                iListener.onUserDetailsReceived("Unknown user");
            }
        });
    }

    private interface OnUserDetailsListener {
        void onUserDetailsReceived(String iUsername);
    }
}