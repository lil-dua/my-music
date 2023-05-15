package com.android.mymusic.service;

import com.android.mymusic.model.Advertisement;
import com.android.mymusic.model.Album;
import com.android.mymusic.model.Category;
import com.android.mymusic.model.CategoryTheme;
import com.android.mymusic.model.Playlist;
import com.android.mymusic.model.Songs;
import com.android.mymusic.model.Theme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {

    //Get data of advertisement from server
    @GET("songbanner.php")
    Call<List<Advertisement>> GetDataBanner();

    //Get data of playlist from server
    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> GetPLaylist();

    //Get data of category and theme from server
    @GET("themencategory.php")
    Call<CategoryTheme> GetCategoryTheme();

    //Get data of album from server
    @GET("albumhot.php")
    Call<List<Album>> GetAlbum();

    //Get data of top favorite songs from server
    @GET("topfavoritesongs.php")
    Call<List<Songs>> GetFavoriteSong();

    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Songs>> GetListSongWithAdvertisement(@Field("idAdvertisement") String idAdvertisement);

    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Songs>> GetListSongWithPlaylist(@Field("idPlaylist") String idPlaylist);

    //Get data of more play list
    @GET("playlistfile.php")
    Call<List<Playlist>> GetMorePlaylist();

    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Songs>> GetListSongWithCategory(@Field("idCategory") String idCategory);

    @GET("moreTheme.php")
    Call<List<Theme>> GetMoreTheme();

    @FormUrlEncoded
    @POST("categorywiththeme.php")
    Call<List<Category>> GetCategoryWithTheme(@Field("idTheme") String idTheme);

    @GET("morealbum.php")
    Call<List<Album>> GetMoreAlbum();

    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Songs>> GetListSongWithAlbum(@Field("idAlbum") String idAlbum);

    @FormUrlEncoded
    @POST("updatelikes.php")
    Call<String> UpdateLikes(@Field("likes") String likes,@Field("idSong") String idSong);

    @FormUrlEncoded
    @POST("search.php")
    Call<List<Songs>> GetSearchSong(@Field("keyword") String keyword);
}
