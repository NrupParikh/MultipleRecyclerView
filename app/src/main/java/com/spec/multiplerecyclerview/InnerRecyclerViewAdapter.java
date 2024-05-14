package com.spec.multiplerecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {

    private List<InnerItem> innerItems;

    public InnerRecyclerViewAdapter(List<InnerItem> innerItems) {
        this.innerItems = innerItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InnerItem item = innerItems.get(position);
        holder.title.setText(item.getTitle() + position);
        holder.description.setText(item.getDescription());
        // Bind data to holder views (e.g., TextView, ImageView)
    }

    @Override
    public int getItemCount() {
        return innerItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // UI elements for inner item layout
        private TextView title;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.inner_item_title);
            description = itemView.findViewById(R.id.inner_item_description);


        }
    }
}
