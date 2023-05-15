package com.android.mymusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.mymusic.fragment.Fragment_Album;
import com.android.mymusic.fragment.Fragment_Home;
import com.android.mymusic.fragment.Fragment_Search;
import com.android.mymusic.fragment.Fragment_TopFavoriteSong;

public class MyViewPagerAdapter extends FragmentStateAdapter {


    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){

            case 1:
                return new Fragment_Search();
            case 2:
                return new Fragment_TopFavoriteSong();
            case 0:
            default:
                return new Fragment_Home();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
