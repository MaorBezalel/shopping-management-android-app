package com.hit.shoppingmanagementapp.models;

public class UserModel {
    private String mEmail;
    private String mUsername;
    private String mMobile;

    public UserModel(String email, String username, String mobile) {
        this.mEmail = email;
        this.mUsername = username;
        this.mMobile = mobile;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getMobile() {
        return mMobile;
    }
}
