package com.android.mymusic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theme implements Serializable {

    @SerializedName("IdTheme")
    @Expose
    private String idTheme;
    @SerializedName("ThemeName")
    @Expose
    private String themeName;
    @SerializedName("ThemeImage")
    @Expose
    private String themeImage;

    public String getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(String idTheme) {
        this.idTheme = idTheme;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeImage() {
        return themeImage;
    }

    public void setThemeImage(String themeImage) {
        this.themeImage = themeImage;
    }

}