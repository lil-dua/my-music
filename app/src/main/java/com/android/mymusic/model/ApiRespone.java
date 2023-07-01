package com.android.mymusic.model;

import com.google.gson.annotations.SerializedName;

/***
 * Created by HoangRyan aka LilDua on 7/2/2023.
 */
public class ApiRespone {
    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("email")
    private String email;

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getEmail() {
        return email;
    }
}
