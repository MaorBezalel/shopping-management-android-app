package com.hit.shoppingmanagementapp.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.adapters.ShoppingItemsRecyclerAdapter;
import com.hit.shoppingmanagementapp.models.ShoppingItemModel;

public class RemoveShoppingItemDialogFragment extends DialogFragment {
    public static final String TAG = "RemoveShoppingItemDialogFragment";

    private ShoppingItemsRecyclerAdapter mRecyclerAdapter;
    private ShoppingItemModel mItem;
    private int mPosition;

    private ImageButton mCloseDialogButton;
    private TextView mAmountToRemoveTextView;
    private SeekBar mAmountToRemoveSeekBar;
    private MaterialButton mRemoveShoppingItemButton;

    public RemoveShoppingItemDialogFragment(ShoppingItemsRecyclerAdapter adapter, ShoppingItemModel item, int position) {
        mRecyclerAdapter = adapter;
        mItem = item;
        mPosition = position;
    }

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater iInflater, ViewGroup iContainer,
                             Bundle iSavedInstanceState) {
        return iInflater.inflate(R.layout.fragment_remove_shopping_item_dialog, iContainer, false);
    }

    @Override
    public void onViewCreated(@NonNull View iView, Bundle iSavedInstanceState) {
        super.onViewCreated(iView, iSavedInstanceState);

        initViews(iView);
        setSeekBarMinAndMaxValues();
        setOnClickListeners();
    }

    private void initViews(@NonNull View iView) {
        mCloseDialogButton = iView.findViewById(R.id.removeShoppingItemDialogCloseBtn);
        mAmountToRemoveTextView = iView.findViewById(R.id.removeShoppingItemDialogAmount);
        mAmountToRemoveSeekBar = iView.findViewById(R.id.removeShoppingItemDialogAmountSeekBar);
        mRemoveShoppingItemButton = iView.findViewById(R.id.removeShoppingItemDialogConfirmBtn);
    }

    private void setSeekBarMinAndMaxValues() {
        mAmountToRemoveTextView.setText("1");

        if (mItem.getItemAmount() > 1) {
            mAmountToRemoveSeekBar.setMax(mItem.getItemAmount() - 1); // workaround to have '1' as the minimum value
        }
        else {
            mAmountToRemoveSeekBar.setMax(2);
            mAmountToRemoveSeekBar.setProgress(2);
            mAmountToRemoveSeekBar.setEnabled(false);
        }
    }

    private void setOnClickListeners() {
        mCloseDialogButton.setOnClickListener((v) -> dismiss());

        mAmountToRemoveSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar iSeekBar, int iProgress, boolean iFromUser) {
                int actualValue = iProgress + 1; // workaround to have '1' as the minimum value
                mAmountToRemoveTextView.setText(String.valueOf(actualValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar iSeekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar iSeekBar) {
            }
        });

        mRemoveShoppingItemButton.setOnClickListener((v) -> {
            int amountToRemove = Integer.parseInt(mAmountToRemoveTextView.getText().toString());

            if (amountToRemove == mItem.getItemAmount()) {
                mRecyclerAdapter.removeItem(mPosition);
            } else {
                int newAmount = mItem.getItemAmount() - amountToRemove;
                double newPrice = mItem.getItemPrice() / mItem.getItemAmount() * newAmount;

                mItem.setItemAmount(newAmount);
                mItem.setItemPrice(newPrice);
                mRecyclerAdapter.updateItem(mPosition, mItem);
            }

            dismiss();
        });
    }
}