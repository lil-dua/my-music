package com.android.mymusic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/***
 * Created by HoangRyan aka LilDua on 7/3/2023.
 */
public class User implements Serializable{

    @SerializedName("id")
    @Expose
    private String idUser;

    @SerializedName("email")
    @Expose
    private String emailUser;

    @SerializedName("password")
    @Expose
    private String passwordUser;

    @SerializedName("username")
    @Expose
    private String nameUser;
    @SerializedName("phone")
    @Expose
    private String phoneUser;
    @SerializedName("image")
    @Expose
    private String imageUser;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }
}
