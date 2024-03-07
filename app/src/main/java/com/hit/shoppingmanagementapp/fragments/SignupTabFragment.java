package com.hit.shoppingmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.activities.AuthActivity;
import com.hit.shoppingmanagementapp.models.UserModel;
import com.hit.shoppingmanagementapp.utils.FirebaseUtils;
import com.hit.shoppingmanagementapp.utils.AuthValidationUtils;
import com.hit.shoppingmanagementapp.activities.MainActivity;

public class SignupTabFragment extends Fragment {

    private TextInputLayout mUsernameTextInputLayout;
    private TextInputLayout mMobileTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;
    private TextInputLayout mConfirmPasswordTextInputLayout;
    private UserModel mUserModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater iInflater, ViewGroup iContainer,
                             Bundle iSavedInstanceState) {
        return iInflater.inflate(R.layout.fragment_signup_tab, iContainer, false);
    }

    @Override
    public void onViewCreated(@NonNull View iView, Bundle iSavedInstanceState) {
        super.onViewCreated(iView, iSavedInstanceState);
        initViews(iView);
        setListeners(iView);
    }

    public void onSignupButtonClicked(View iView) {
        String username = mUsernameTextInputLayout.getEditText().getText().toString().trim();
        String mobile = mMobileTextInputLayout.getEditText().getText().toString().trim();
        String password = mPasswordTextInputLayout.getEditText().getText().toString().trim();
        String confirmPassword = mConfirmPasswordTextInputLayout.getEditText().getText().toString().trim();
        String email = username.toLowerCase() + "@shoppingapp.com";

        if (AuthValidationUtils.IsUserSignupDetailsValid(mUsernameTextInputLayout, mMobileTextInputLayout, mPasswordTextInputLayout, mConfirmPasswordTextInputLayout)) {
            mUserModel = new UserModel(email, username, mobile);
            try {
                FirebaseUtils.createUserAccount(email, password, mUserModel, (AuthActivity) getActivity(), this);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Signup failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onUserSignupSuccess() {
        Toast.makeText(getContext(), "Signup successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void onUserSignupFailure(@NonNull String iInvalidationCode) {
        switch (iInvalidationCode) {
            case "ERROR_EMAIL_ALREADY_IN_USE":
                mUsernameTextInputLayout.setError("username already in use");
                break;
            case "ERROR_WEAK_PASSWORD":
                mPasswordTextInputLayout.setError("weak password");
                break;
            default:
                Toast.makeText(getContext(), "Signup failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(@NonNull View iView) {
        mUsernameTextInputLayout = iView.findViewById(R.id.signupUsernameLayout);
        mMobileTextInputLayout = iView.findViewById(R.id.signupMobileLayout);
        mPasswordTextInputLayout = iView.findViewById(R.id.signupPasswordLayout);
        mConfirmPasswordTextInputLayout = iView.findViewById(R.id.signupConfirmPasswordLayout);
    }

    private void setListeners(@NonNull View iView) {
        iView.findViewById(R.id.signupBtn).setOnClickListener(this::onSignupButtonClicked);
    }
}