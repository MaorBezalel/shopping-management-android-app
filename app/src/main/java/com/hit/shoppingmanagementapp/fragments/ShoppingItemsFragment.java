package com.hit.shoppingmanagementapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.adapters.ShoppingItemsRecyclerAdapter;
import com.hit.shoppingmanagementapp.models.ShoppingItemModel;
import com.hit.shoppingmanagementapp.utils.FirebaseUtils;

import org.jetbrains.annotations.Contract;

public class ShoppingItemsFragment extends Fragment {
    public static final String TAG = "ShoppingItemsFragment";

    private ShoppingItemsRecyclerAdapter mRecyclerAdapter;

    private RecyclerView mShoppingItemsRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ImageButton mOpenAddShoppingItemDialogButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater iInflater, ViewGroup iContainer,
                             Bundle iSavedInstanceState) {
        return iInflater.inflate(R.layout.fragment_shopping_items, iContainer, false);
    }

    @Override
    public void onViewCreated(@NonNull View iView, Bundle iSavedInstanceState) {
        super.onViewCreated(iView, iSavedInstanceState);

        initViews(iView);
        setUpRecyclerView(iView);
        setUpAdapter();
        setOnClickListeners();
    }

    public void openAddShoppingItemDialog(@NonNull View iView) {
        AddShoppingItemDialogFragment dialogFragment = new AddShoppingItemDialogFragment();
        dialogFragment.show(getChildFragmentManager(), AddShoppingItemDialogFragment.TAG);
    }

    private void initViews(@NonNull View iView) {
        mOpenAddShoppingItemDialogButton = iView.findViewById(R.id.openAddShoppingItemDialogBtn);
        mLayoutManager = new LinearLayoutManager(getContext());
        mShoppingItemsRecyclerView = iView.findViewById(R.id.shoppingItemsRecyclerView);
    }

    private void setUpRecyclerView(@NonNull View iView) {
        mShoppingItemsRecyclerView.setLayoutManager(mLayoutManager);
        mShoppingItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setUpAdapter() {
        String collectionPath = "shoppingitems";
        String fieldToQueryBy = "user_id";
        String valueToQueryBy = FirebaseUtils.getCurrentUserId();

        Query query = FirebaseUtils.getDB()
                .collection(collectionPath)
                .limit(20)
                .whereEqualTo(fieldToQueryBy, valueToQueryBy);


        FirestoreRecyclerOptions<ShoppingItemModel> options = new FirestoreRecyclerOptions.Builder<ShoppingItemModel>()
                .setQuery(query, ShoppingItemModel.class)
                .build();

        mRecyclerAdapter = new ShoppingItemsRecyclerAdapter(options);
        mShoppingItemsRecyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.startListening(); // Maybe move it to `onStart` and `onStop` methods?
    }

    private void setOnClickListeners() {
        mOpenAddShoppingItemDialogButton.setOnClickListener(this::openAddShoppingItemDialog);
    }
}