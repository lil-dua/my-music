package com.android.mymusic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.mymusic.R;
import com.android.mymusic.adapter.BannerAdapter;
import com.android.mymusic.model.Advertisement;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        init();
        GetData();
        return view;
    }

    private void init() {
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.indicatorDefault);
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Advertisement>> callback = dataservice.GetDataBanner();
        callback.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {

                ArrayList<Advertisement> banner = (ArrayList<Advertisement>) response.body();

                bannerAdapter = new BannerAdapter(getActivity(),banner);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = () -> {
                    currentItem = viewPager.getCurrentItem();
                    currentItem++;
                    //when viewpager go to the end, it with come back to the first viewpager
                    if(currentItem >= viewPager.getAdapter().getCount()){
                        currentItem = 0;
                    }
                    viewPager.setCurrentItem(currentItem,true);
                    //delay for scroll between each other viewpager
                    handler.postDelayed(runnable,9000);
                };
                handler.postDelayed(runnable,9000);
            }

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {

            }
        });
    }
}
