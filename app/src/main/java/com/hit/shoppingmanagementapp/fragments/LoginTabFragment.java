package com.hit.shoppingmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.activities.AuthActivity;
import com.hit.shoppingmanagementapp.activities.MainActivity;
import com.hit.shoppingmanagementapp.utils.FirebaseUtils;
import com.hit.shoppingmanagementapp.utils.AuthValidationUtils;

public class LoginTabFragment extends Fragment {
    private TextInputLayout mUsernameTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater iInflater, ViewGroup iContainer,
                             Bundle iSavedInstanceState) {
        return iInflater.inflate(R.layout.fragment_login_tab, iContainer, false);
    }

    @Override
    public void onViewCreated(@NonNull View iView, Bundle iSavedInstanceState) {
        super.onViewCreated(iView, iSavedInstanceState);
        initViews(iView);
        setListeners(iView);
    }

    public void onLoginButtonClicked(View iView) {
        String username = mUsernameTextInputLayout.getEditText().getText().toString();
        String password = mPasswordTextInputLayout.getEditText().getText().toString();
        String email = username.toLowerCase() + "@shoppingapp.com";

        if (AuthValidationUtils.IsUserLoginDetailsValid(mUsernameTextInputLayout, mPasswordTextInputLayout)) {
            try {
                FirebaseUtils.loginUser(email, password, (AuthActivity) getActivity(), this);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onUserLoginSuccess() {
        Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void onUserLoginFailure(@NonNull String iInvalidationCode) {
        switch (iInvalidationCode) {
            case "ERROR_INVALID_EMAIL":
                mUsernameTextInputLayout.setError("Username is badly formatted");
                break;
            case "ERROR_USER_NOT_FOUND":
                mUsernameTextInputLayout.setError("Username not found");
                break;
            case "ERROR_WRONG_PASSWORD":
                mPasswordTextInputLayout.setError("Wrong password");
                break;
            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(getContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "Login failed: " + iInvalidationCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(@NonNull View iView) {
        mUsernameTextInputLayout = iView.findViewById(R.id.loginUsernameLayout);
        mPasswordTextInputLayout = iView.findViewById(R.id.loginPasswordLayout);
    }

    private void setListeners(@NonNull View iView) {
        iView.findViewById(R.id.loginBtn).setOnClickListener(this::onLoginButtonClicked);
    }
}