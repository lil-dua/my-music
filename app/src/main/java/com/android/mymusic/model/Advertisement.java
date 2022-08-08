package com.android.mymusic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Advertisement implements Serializable {

    @SerializedName("IdAdvertisement")
    @Expose
    private String idAdvertisement;
    @SerializedName("Imaget")
    @Expose
    private String imaget;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("IdSong")
    @Expose
    private String idSong;
    @SerializedName("SongName")
    @Expose
    private String songName;
    @SerializedName("SongImage")
    @Expose
    private String songImage;

    public String getIdAdvertisement() {
        return idAdvertisement;
    }

    public void setIdAdvertisement(String idAdvertisement) {
        this.idAdvertisement = idAdvertisement;
    }

    public String getImaget() {
        return imaget;
    }

    public void setImaget(String imaget) {
        this.imaget = imaget;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

}