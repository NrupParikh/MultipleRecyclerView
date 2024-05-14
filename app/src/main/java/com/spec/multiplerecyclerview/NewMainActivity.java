package com.spec.multiplerecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewMainActivity extends AppCompatActivity {
    private RecyclerView outerRecyclerView;
    private OuterRecyclerViewAdapter outerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int offset = dpToPx(this, 400);
        outerRecyclerView = findViewById(R.id.outer_recycler_view);
        outerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        outerRecyclerView.setLayoutManager(smoothScroller.getLayoutManager());

        // Prepare sample data
        List<OuterItem> outerItems = prepareSampleData();
        outerAdapter = new OuterRecyclerViewAdapter(outerItems);
        outerRecyclerView.setAdapter(outerAdapter);

        smoothScrollToThenBy(outerRecyclerView, 9, 0, offset);
//        outerRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                outerRecyclerView.smoothScrollToPosition(19);
//                smoothScrollToThenBy(outerRecyclerView, 20, 0, 0);
//            }
//        }, 2000);

//        outerRecyclerView.smoothScrollBy(0, offset);


        // Scroll to 3rd parent's 2nd child on activity load
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                scrollToPosition(3, 2);
//            }
//        },2000);

    }

    public static int dpToPx(Context context, float dp) {
        final float density = context.getResources().getDisplayMetrics().density;
        int px = Math.round(dp * density);
        return px;
    }

    private List<OuterItem> prepareSampleData() {
        List<OuterItem> outerItems = new ArrayList<>();

        // Create OuterItems with varying inner item counts
        for (int i = 0; i < 125; i++) {
            OuterItem outerItem = new OuterItem();
            outerItem.setCategory("Category " + (i + 1));
            List<InnerItem> innerItems = new ArrayList<>();
            for (int j = 0; j < i+3; j++) { // Vary inner item count
                InnerItem innerItem = new InnerItem();
                innerItem.setTitle("Inner Item " + (j + 1));
                innerItem.setDescription("Description for inner item");
                innerItems.add(innerItem);
            }
            outerItem.setInnerItems(innerItems);
            outerItems.add(outerItem);
        }

        return outerItems;
    }

    private void scrollToPosition(int parentPosition, int childPosition) {
        if (outerAdapter != null && outerAdapter.getItemCount() > parentPosition) {
            // Get the OuterItem at the specified parent position
            OuterItem outerItem = outerAdapter.outerItems.get(parentPosition);

            // Ensure child position is within bounds
            if (childPosition >= 0 && childPosition < outerItem.getInnerItems().size()) {
                // Find the ViewHolder for the parent item
                RecyclerView.ViewHolder parentViewHolder = outerRecyclerView.findViewHolderForAdapterPosition(parentPosition);
                if (parentViewHolder != null && parentViewHolder.itemView instanceof ViewGroup) {
                    ViewGroup parentView = (ViewGroup) parentViewHolder.itemView;

                    // Get the nested RecyclerView from the parent view (assuming it has an ID)
                    RecyclerView innerRecyclerView = parentView.findViewById(R.id.inner_recycler_view);

                    // Check if inner RecyclerView exists and is attached
                    if (innerRecyclerView != null && innerRecyclerView.isAttachedToWindow()) {
                        // Layout manager might need to be set for the inner RecyclerView
                        if (innerRecyclerView.getLayoutManager() == null) {
                            innerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                        }

                        // Get the adapter for the inner RecyclerView
                        InnerRecyclerViewAdapter innerAdapter = (InnerRecyclerViewAdapter) innerRecyclerView.getAdapter();

                        // Ensure child position is within the inner adapter's data
                        if (innerAdapter != null && innerAdapter.getItemCount() > childPosition) {
                            // Smooth scroll to the child position in the inner RecyclerView
                            innerRecyclerView.smoothScrollToPosition(childPosition);
                        }
                    }
                }
            }
        }
    }

    public class MySmoothScroller extends LinearSmoothScroller {

        public MySmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            // Adjust scrolling speed based on preferences (optional)
            return super.calculateSpeedPerPixel(displayMetrics);
        }
    }

//    public static void smoothScrollToThenBy(RecyclerView recyclerView, final int targetPosition, final int dx, final int dy) {
//        recyclerView.smoothScrollToPosition(targetPosition);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    recyclerView.removeOnScrollListener(this);
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollBy(dx, dy);
//                        }
//                    }, 100); // Adjust delay as needed (in milliseconds)
//                }
//            }
//        });
//    }

    public static void smoothScrollToThenBy(RecyclerView recyclerView, final int targetPosition, final int dx, final int dy) {
        recyclerView.smoothScrollToPosition(targetPosition);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView.removeOnScrollListener(this);

                    // Get LayoutManager
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    // Check if target position is valid
                    if (targetPosition < layoutManager.getItemCount()) {
                        // Find the target view
                        View targetView = layoutManager.findViewByPosition(targetPosition);

                        if (targetView != null) {
                            // Calculate on-screen position (considering previous items)
                            int targetViewTop = targetView.getTop() - recyclerView.getPaddingTop();

                            recyclerView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Perform smooth scroll by with adjusted offset
                                    recyclerView.smoothScrollBy(dx, targetViewTop + dy);
                                }
                            }, 100); // Adjust delay as needed
                        }
                    }
                }
            }
        });
    }


}
