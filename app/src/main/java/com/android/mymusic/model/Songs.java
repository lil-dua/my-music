package com.android.mymusic.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Songs implements Parcelable {

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

    protected Songs(Parcel in) {
        idSong = in.readString();
        songName = in.readString();
        songImage = in.readString();
        singer = in.readString();
        songLink = in.readString();
        likes = in.readString();
    }

    public static final Creator<Songs> CREATOR = new Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idSong);
        parcel.writeString(songName);
        parcel.writeString(songImage);
        parcel.writeString(singer);
        parcel.writeString(songLink);
        parcel.writeString(likes);
    }
}
