package com.hit.shoppingmanagementapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.models.ShoppingItemModel;
import com.hit.shoppingmanagementapp.utils.FirebaseUtils;

public class AddShoppingItemDialogFragment extends DialogFragment {
    public static final String TAG = "AddShoppingItemDialogFragment";

    private ImageButton mCloseDialogButton;
    private MaterialButton mAddShoppingItemButton;
    private ImageView mItemCategoryImage;
    private TextInputLayout mItemNameInput;
    private TextInputLayout mItemPriceInput;
    private TextInputLayout mItemAmountInput;
    private Spinner mItemCategorySpinner;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setDimAmount(0.5f); // Set your desired dim amount here
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void onCreate(Bundle iSavedInstanceState) {
        super.onCreate(iSavedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent_NoTitleBar);
//        setStyle(DialogFragment.STYLE_NO_TITLE,
//                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater iInflater, ViewGroup iContainer,
                             Bundle iSavedInstanceState) {
        return iInflater.inflate(R.layout.fragment_add_shopping_item_dialog, iContainer, false);
    }

    @Override
    public void onViewCreated(@NonNull View iView, Bundle iSavedInstanceState) {
        super.onViewCreated(iView, iSavedInstanceState);

        initDialogViews(iView);
        setArrayAdapterForItemCategoryInput(iView);
        setUpButtonClickListeners();
    }

    private void initDialogViews(@NonNull View iView) {
        mCloseDialogButton = iView.findViewById(R.id.addShoppingItemDialogCloseBtn);
        mAddShoppingItemButton = iView.findViewById(R.id.addShoppingItemDialogAddBtn);
        mItemCategoryImage = iView.findViewById(R.id.addShoppingItemCategoryImage);
        mItemNameInput = iView.findViewById(R.id.addShoppingItemNameLayout);
        mItemPriceInput = iView.findViewById(R.id.addShoppingItemPriceLayout);
        mItemAmountInput = iView.findViewById(R.id.addShoppingItemAmountLayout);
        mItemCategorySpinner = iView.findViewById(R.id.addShoppingItemCategorySpinner);

        Log.d(TAG, "mAddShoppingItemButton after init: " + mAddShoppingItemButton);
    }

    private void setArrayAdapterForItemCategoryInput(@NonNull View iView) {
        String[] categories = getResources().getStringArray(R.array.shopping_item_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        mItemCategorySpinner.setAdapter(adapter);
    }

    private void setUpButtonClickListeners() {
        mAddShoppingItemButton.setOnClickListener(this::onAddShoppingItemButtonClick);
        mCloseDialogButton.setOnClickListener((v) -> dismiss());
    }

    public void onAddShoppingItemButtonClick(@NonNull View iView) {
        if (!areInputsValid()) {
            return;
        }

        ShoppingItemModel shoppingItem = createShoppingItem();
        FirebaseUtils.addShoppingItem(shoppingItem);
        dismiss();
    }

    private boolean areInputsValid() {
        return validatedItemNameInput() && validatedPriceInput() && validatedAmountInput();
    }

    private boolean validatedItemNameInput() {
        boolean isValid = false;
        String itemName = mItemNameInput.getEditText().getText().toString().trim();

        if (itemName.isEmpty()) {
            mItemNameInput.setError("Item name is required");
        } else if (itemName.length() > 15) {
            mItemNameInput.setError("Item name is too long");
        } else {
            mItemNameInput.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private boolean validatedPriceInput() {
        boolean isValid = false;
        String itemPrice = mItemPriceInput.getEditText().getText().toString().trim();

        if (itemPrice.isEmpty()) {
            mItemPriceInput.setError("Item price is required");
        } else if (Double.parseDouble(itemPrice) <= 0) {
            mItemPriceInput.setError("Item price must be greater than 0");
        } else {
            mItemPriceInput.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private boolean validatedAmountInput() {
        boolean isValid = false;
        String itemAmount = mItemAmountInput.getEditText().getText().toString().trim();

        if (itemAmount.isEmpty()) {
            mItemAmountInput.setError("Item amount is required");
        } else if (Integer.parseInt(itemAmount) <= 0) {
            mItemAmountInput.setError("Item amount must be greater than 0");
        } else {
            mItemAmountInput.setError(null);
            isValid = true;
        }

        return isValid;
    }

    @NonNull
    private ShoppingItemModel createShoppingItem() {
        String itemName = mItemNameInput.getEditText().getText().toString().trim();
        double itemPricePerUnit = Double.parseDouble(mItemPriceInput.getEditText().getText().toString().trim());
        int itemAmount = Integer.parseInt(mItemAmountInput.getEditText().getText().toString().trim());
        String itemCategory = mItemCategorySpinner.getSelectedItem().toString().trim();
        double itemPrice = itemPricePerUnit * itemAmount;

        return new ShoppingItemModel(itemName, itemPrice, itemAmount, itemCategory);
    }
}