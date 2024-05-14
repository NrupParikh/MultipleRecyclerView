package com.spec.multiplerecyclerview;

import java.util.List;

public class OuterItem {
    private String category;
    private List<InnerItem> innerItems;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<InnerItem> getInnerItems() {
        return innerItems;
    }

    public void setInnerItems(List<InnerItem> innerItems) {
        this.innerItems = innerItems;
    }
    // Getters and setters
}