package com.android.mymusic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("IdCategory")
    @Expose
    private String idCategory;
    @SerializedName("IdKeyTheme")
    @Expose
    private String idKeyTheme;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("CategoryImage")
    @Expose
    private String categoryImage;

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdKeyTheme() {
        return idKeyTheme;
    }

    public void setIdKeyTheme(String idKeyTheme) {
        this.idKeyTheme = idKeyTheme;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

}