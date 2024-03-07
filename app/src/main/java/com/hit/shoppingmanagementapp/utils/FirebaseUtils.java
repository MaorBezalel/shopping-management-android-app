package com.hit.shoppingmanagementapp.utils;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hit.shoppingmanagementapp.activities.AuthActivity;
import com.hit.shoppingmanagementapp.fragments.LoginTabFragment;
import com.hit.shoppingmanagementapp.fragments.SignupTabFragment;
import com.hit.shoppingmanagementapp.models.ShoppingItemModel;
import com.hit.shoppingmanagementapp.models.UserModel;


public class FirebaseUtils {
    private FirebaseUtils() { } // static class

    @NonNull
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    @NonNull
    public static FirebaseFirestore getDB() {
        return FirebaseFirestore.getInstance();
    }

    public static String getCurrentUserId() {
        return getAuth().getUid();
    }

    public static void createUserAccount(String iEmail, String iPassword, UserModel iUserModel, AuthActivity iAuthActivity, SignupTabFragment iSignupTabFragment) {
        getAuth().createUserWithEmailAndPassword(iEmail, iPassword)
                .addOnCompleteListener(iAuthActivity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUtils.getCurrentUserDetails().set(iUserModel);
                        iSignupTabFragment.onUserSignupSuccess();
                    } else {
                        String invalidationCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        iSignupTabFragment.onUserSignupFailure(invalidationCode);
                    }
                });
    }

    public static void loginUser(String iEmail, String iPassword, AuthActivity iAuthActivity, LoginTabFragment iLoginTabFragment) {
        getAuth().signInWithEmailAndPassword(iEmail, iPassword)
                .addOnCompleteListener(iAuthActivity, task -> {
                    if (task.isSuccessful()) {
                        iLoginTabFragment.onUserLoginSuccess();
                    } else {
                        String invalidationCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        iLoginTabFragment.onUserLoginFailure(invalidationCode);
                    }
                });
    }

    @NonNull
    public static DocumentReference getCurrentUserDetails() {
        return getDB().collection("users").document(getCurrentUserId());
    }

    public static boolean isUserLoggedIn() {
        return getAuth().getCurrentUser() != null;
    }

    public static void addShoppingItem(ShoppingItemModel iShoppingItemModel) {
        getDB().collection("shoppingitems").add(iShoppingItemModel);
    }
}
