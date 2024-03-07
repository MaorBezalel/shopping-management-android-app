package com.hit.shoppingmanagementapp.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.PropertyName;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.utils.FirebaseUtils;

import org.jetbrains.annotations.Contract;


public class ShoppingItemModel {
    @PropertyName("name")
    private String mItemName;

    @PropertyName("amount")
    private int mItemAmount;

    @PropertyName("price")
    private double mItemPrice;

    @PropertyName("category")
    private String mItemCategory;

    @PropertyName("user_id")
    private String mUserId;

    public ShoppingItemModel() {
        // Required empty public constructor for Firestore
    }

    public ShoppingItemModel(String iItemName, double iItemPrice, int iItemAmount, String iItemCategory) {
        mItemName = iItemName;
        mItemPrice = iItemPrice;
        mItemAmount = iItemAmount;
        mItemCategory = iItemCategory;
        mUserId = FirebaseUtils.getCurrentUserId();
    }

    @PropertyName("name") public String getItemName() {
        return mItemName;
    }
    @PropertyName("name") public void setItemName(String iItemName) {
        mItemName = iItemName;
    }

    @PropertyName("amount") public int getItemAmount() {
        return mItemAmount;
    }
    @PropertyName("amount") public void setItemAmount(int iItemAmount) {
        mItemAmount = iItemAmount;
    }

    @PropertyName("price") public double getItemPrice() {
        return mItemPrice;
    }
    @PropertyName("price") public void setItemPrice(double iItemPrice) {
        mItemPrice = iItemPrice;
    }

    @PropertyName("category") public String getItemCategory() {
        return mItemCategory;
    }
    @PropertyName("category") public void setItemCategory(String iItemCategory) {
        mItemCategory = iItemCategory;
    }

    @PropertyName("user_id") public String getUserId() {
        return mUserId;
    }
    @PropertyName("user_id") public void setUserId(String iUserId) {
        mUserId = iUserId;
    }

    @Contract(pure = true)
    public static int getTheCorrectImageAccordingToTheCategory(@NonNull String iCategory) {
        switch (iCategory) {
            case "clothes": return R.drawable.clothes;
            case "electronics": return R.drawable.electronics;
            case "furniture": return R.drawable.furniture;
            case "groceries": return R.drawable.groceries;
            case "medicine": return R.drawable.medicine;
            case "other": return R.drawable.other;
            default: return R.drawable.other;
        }
    }
}
