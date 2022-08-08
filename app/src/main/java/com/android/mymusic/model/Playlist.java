package com.android.mymusic.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Playlist implements Serializable {

    @SerializedName("IdPlaylist")
    @Expose
    private String idPlaylist;
    @SerializedName("PlaylistName")
    @Expose
    private String playlistName;
    @SerializedName("PlaylistImage")
    @Expose
    private String playlistImage;
    @SerializedName("PlaylistIcon")
    @Expose
    private String playlistIcon;

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistImage() {
        return playlistImage;
    }

    public void setPlaylistImage(String playlistImage) {
        this.playlistImage = playlistImage;
    }

    public String getPlaylistIcon() {
        return playlistIcon;
    }

    public void setPlaylistIcon(String playlistIcon) {
        this.playlistIcon = playlistIcon;
    }

}