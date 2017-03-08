package me.jaxvy.boilerplate.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.model.persistence.Item;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> mItemList;

    public void update(List<Item> itemList) {
        mItemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.onBind(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mItemList == null) {
            return 0;
        } else {
            return mItemList.size();
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.AdapterItem_title)
        TextView mTitle;

        @BindView(R.id.AdapterItem_description)
        TextView mDescription;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Item item) {
            mTitle.setText(item.getTitle());
            mDescription.setText(item.getDescription());
        }
    }
}
