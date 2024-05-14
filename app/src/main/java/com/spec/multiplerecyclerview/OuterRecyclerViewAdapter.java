package com.spec.multiplerecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OuterRecyclerViewAdapter extends RecyclerView.Adapter<OuterRecyclerViewAdapter.ViewHolder> {

    List<OuterItem> outerItems;

    public OuterRecyclerViewAdapter(List<OuterItem> outerItems) {
        this.outerItems = outerItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outer_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OuterItem outerItem = outerItems.get(position);
        holder.innerRecyclerView.setAdapter(new InnerRecyclerViewAdapter(outerItem.getInnerItems()));
        holder.parent.setText(outerItem.getCategory());
        // Expand/collapse logic if needed
    }

    @Override
    public int getItemCount() {
        return outerItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // UI elements for outer item layout (including inner RecyclerView)
        private RecyclerView innerRecyclerView;
        private TextView parent;

        public ViewHolder(View itemView) {
            super(itemView);
            innerRecyclerView = itemView.findViewById(R.id.inner_recycler_view);
            parent = itemView.findViewById(R.id.outer_item_category);
            // Set layout manager (optional, depending on desired layout)
        }
    }
}