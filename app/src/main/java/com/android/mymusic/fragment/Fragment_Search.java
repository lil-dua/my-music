package com.android.mymusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.activity.MoreAlbumActivity;
import com.android.mymusic.activity.MorePlaylistActivity;
import com.android.mymusic.activity.MoreThemeActivity;
import com.android.mymusic.adapter.SearchSongAdapter;
import com.android.mymusic.model.Songs;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Search extends Fragment {

    View view;
    Toolbar toolbarSearch;
    RecyclerView recyclerViewSearchSong;
    TextView txtNoneData;
    CardView cardViewAlbum, cardViewPlaylist, cardViewTheme, cardViewCategories;
    EditText editTextQuery;
    ImageView imageViewSearch,imageViewCancel;
    SearchSongAdapter searchSongAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_search,container,false);
        Init();
        Action();
        return view;
    }

    private void Action() {
        // Open more list of songs
        cardViewAlbum.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MoreAlbumActivity.class));
        });
        cardViewPlaylist.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MorePlaylistActivity.class));
        });
        cardViewTheme.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MoreThemeActivity.class));
        });
        cardViewCategories.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MoreThemeActivity.class));
        });

        //Search with keyword
        imageViewSearch.setOnClickListener(v -> {
            SearchKeyWord(editTextQuery.getText().toString().trim());
        });

        imageViewCancel.setOnClickListener(v -> {
            recyclerViewSearchSong.setVisibility(View.GONE);
            imageViewCancel.setVisibility(View.GONE);
            editTextQuery.setText("");
        });

    }

    private void Init() {
        recyclerViewSearchSong = view.findViewById(R.id.recycleViewSearchSong);
        txtNoneData = view.findViewById(R.id.textViewNoData);
        // Mapping
        cardViewAlbum = view.findViewById(R.id.cardView_AlBum);
        cardViewPlaylist = view.findViewById(R.id.cardView_Playlist);
        cardViewTheme = view.findViewById(R.id.cardView_Theme);
        cardViewCategories = view.findViewById(R.id.cardView_Category);

        editTextQuery = view.findViewById(R.id.editText_query);
        imageViewSearch = view.findViewById(R.id.iconSearch);
        imageViewCancel = view.findViewById(R.id.imageViewCancel);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchKeyWord(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void SearchKeyWord(String query){
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.GetSearchSong(query);
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                ArrayList<Songs> songsArrayList = (ArrayList<Songs>) response.body();
                if(songsArrayList.size() > 0){
                    searchSongAdapter = new SearchSongAdapter(getActivity(),songsArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewSearchSong.setLayoutManager(linearLayoutManager);
                    recyclerViewSearchSong.setAdapter(searchSongAdapter);

                    txtNoneData.setVisibility(View.GONE);
                    recyclerViewSearchSong.setVisibility(View.VISIBLE);
                    imageViewCancel.setVisibility(View.VISIBLE);
                }else {
                    recyclerViewSearchSong.setVisibility(View.GONE);
                    imageViewCancel.setVisibility(View.VISIBLE);
                    txtNoneData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }
}
