package com.android.mymusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
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
    SearchSongAdapter searchSongAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_search,container,false);
        toolbarSearch = view.findViewById(R.id.toolbarSearchSong);
        recyclerViewSearchSong = view.findViewById(R.id.recycleViewSearchSong);
        txtNoneData = view.findViewById(R.id.textViewNoData);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarSearch);
        toolbarSearch.setTitle("Search");
        setHasOptionsMenu(true);
        return view;
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
                }else {
                    recyclerViewSearchSong.setVisibility(View.GONE);
                    txtNoneData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }
}
