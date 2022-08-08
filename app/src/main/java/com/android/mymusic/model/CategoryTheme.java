package com.android.mymusic.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryTheme {

    @SerializedName("Category")
    @Expose
    private List<Category> category = null;
    @SerializedName("Theme")
    @Expose
    private List<Theme> theme = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Theme> getTheme() {
        return theme;
    }

    public void setTheme(List<Theme> theme) {
        this.theme = theme;
    }

}