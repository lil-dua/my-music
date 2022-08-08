package com.android.mymusic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopFavoriteSong implements Serializable {

    @SerializedName("IdSong")
    @Expose
    private String idSong;
    @SerializedName("SongName")
    @Expose
    private String songName;
    @SerializedName("SongImage")
    @Expose
    private String songImage;
    @SerializedName("Singer")
    @Expose
    private String singer;
    @SerializedName("SongLink")
    @Expose
    private String songLink;
    @SerializedName("Likes")
    @Expose
    private String likes;

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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

}