package com.hit.shoppingmanagementapp.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hit.shoppingmanagementapp.R;
import com.hit.shoppingmanagementapp.activities.MainActivity;
import com.hit.shoppingmanagementapp.fragments.RemoveShoppingItemDialogFragment;
import com.hit.shoppingmanagementapp.models.ShoppingItemModel;

public class ShoppingItemsRecyclerAdapter extends FirestoreRecyclerAdapter<ShoppingItemModel, ShoppingItemsRecyclerAdapter.ShoppingItemViewHolder> {
    public ShoppingItemsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ShoppingItemModel> iOptions) {
        super(iOptions);
    }

    public static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {
        public final CardView ITEM_CARD;
        public final ImageView ITEM_IMAGE;
        public final TextView ITEM_NAME;
        public final TextView ITEM_PRICE;
        public final TextView ITEM_AMOUNT;
        public final ImageButton ITEM_DELETE_BUTTON;

        public ShoppingItemViewHolder(@NonNull View iItemView) {
            super(iItemView);

            ITEM_CARD = iItemView.findViewById(R.id.shoppingItemCard);
            ITEM_IMAGE = iItemView.findViewById(R.id.shoppingItemImage);
            ITEM_NAME = iItemView.findViewById(R.id.shoppingItemName);
            ITEM_PRICE = iItemView.findViewById(R.id.shoppingItemPrice);
            ITEM_AMOUNT = iItemView.findViewById(R.id.shoppingItemAmount);
            ITEM_DELETE_BUTTON = iItemView.findViewById(R.id.shoppingItemDeleteBtn);
        }
    }

    @NonNull
    @Override
    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup iParent, int iViewType) {
        return new ShoppingItemViewHolder(LayoutInflater.from(iParent.getContext()).inflate(R.layout.view_card_shopping_item, iParent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ShoppingItemViewHolder iHolder, int iPosition, @NonNull ShoppingItemModel iModel) {
        int itemImage = ShoppingItemModel.getTheCorrectImageAccordingToTheCategory(iModel.getItemCategory());
        int itemDeleteButtonImage = R.drawable.ic_delete_trash_can;
        String itemName = iModel.getItemName();
        String itemAmount = "Amount: " + iModel.getItemAmount();
        String itemPrice = "Price: " + iModel.getItemPrice() + " $";

        iHolder.ITEM_IMAGE.setImageResource(itemImage);
        iHolder.ITEM_NAME.setText(itemName);
        iHolder.ITEM_AMOUNT.setText(itemAmount);
        iHolder.ITEM_PRICE.setText(itemPrice);
        iHolder.ITEM_DELETE_BUTTON.setImageResource(itemDeleteButtonImage);

        iHolder.ITEM_DELETE_BUTTON.setOnClickListener((v) -> {
            FragmentManager fragmentManager = ((MainActivity) v.getContext()).getSupportFragmentManager();
            RemoveShoppingItemDialogFragment dialogFragment = new RemoveShoppingItemDialogFragment(this, iModel, iPosition);
            dialogFragment.show(fragmentManager, RemoveShoppingItemDialogFragment.TAG);
        });
    }

    public void removeItem(int iPosition) {
        getSnapshots().getSnapshot(iPosition).getReference().delete();
    }

    public void updateItem(int iPosition, ShoppingItemModel iNewItem) {
        getSnapshots().getSnapshot(iPosition).getReference().set(iNewItem);
    }
}