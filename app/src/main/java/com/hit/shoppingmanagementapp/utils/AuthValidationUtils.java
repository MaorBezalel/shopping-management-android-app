package com.hit.shoppingmanagementapp.utils;

import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

public class AuthValidationUtils {
    private AuthValidationUtils() { } // static class

    public static boolean IsUserLoginDetailsValid(
            @NonNull TextInputLayout iUsernameTextInputLayout,
            @NonNull TextInputLayout iPasswordTextInputLayout
    ) {
        String username = iUsernameTextInputLayout.getEditText().getText().toString().trim();
        String password = iPasswordTextInputLayout.getEditText().getText().toString().trim();

        // Validating username
        if (username.isEmpty()) {
            iUsernameTextInputLayout.setError("Username is required");
            return false;
        } else if (!username.matches("^[a-zA-Z0-9]*$")) {
            iUsernameTextInputLayout.setError("Username can only contain English letters and numbers");
            return false;
        } else {
            iUsernameTextInputLayout.setError(null);
        }

        // Validating password
        if (password.isEmpty()) {
            iPasswordTextInputLayout.setError("Password is required");
            return false;
        } else if (password.length() < 6) {
            iPasswordTextInputLayout.setError("Password must be at least 6 characters long");
            return false;
        } else {
            iPasswordTextInputLayout.setError(null);
        }

        return true;
    }

    public static boolean IsUserSignupDetailsValid(
            @NonNull TextInputLayout iUsernameField,
            @NonNull TextInputLayout iMobileField,
            @NonNull TextInputLayout iPasswordField,
            @NonNull TextInputLayout iConfirmPasswordField
    ) {
        String username = iUsernameField.getEditText().getText().toString().trim();
        String mobile = iMobileField.getEditText().getText().toString().trim();
        String password = iPasswordField.getEditText().getText().toString().trim();
        String confirmPassword = iConfirmPasswordField.getEditText().getText().toString().trim();

        // Validating username
        if (username.isEmpty()) {
            iUsernameField.setError("Username is required");
            return false;
        } else if (!username.matches("^[a-zA-Z0-9]*$")) {
            iUsernameField.setError("Username can only contain English letters and numbers");
            return false;
        } else {
            iUsernameField.setError(null);
        }

        // Validating mobile
        if (mobile.isEmpty()) {
            iMobileField.setError("Mobile is required");
            return false;
        } else if (mobile.length() != 10) {
            iMobileField.setError("Mobile must be 10 digits long");
            return false;
        } else if (!mobile.matches("^[0-9]*$")) {
            iMobileField.setError("Mobile can only contain numbers");
            return false;
        } else {
            iMobileField.setError(null);
        }

        // Validating password
        if (password.isEmpty()) {
            iPasswordField.setError("Password is required");
            return false;
        } else if (password.length() < 6) {
            iPasswordField.setError("Password must be at least 6 characters long");
            return false;
        } else {
            iPasswordField.setError(null);
        }

        // Validating confirm password
        if (confirmPassword.isEmpty()) {
            iConfirmPasswordField.setError("Confirm Password is required");
            return false;
        } else if (!password.equals(confirmPassword)) {
            iConfirmPasswordField.setError("Password does not match");
            return false;
        } else {
            iConfirmPasswordField.setError(null);
        }

        return true;
    }
}
