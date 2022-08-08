package com.android.mymusic.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.mymusic.fragment.Fragment_Music_Dish;
import com.android.mymusic.fragment.Fragment_PLay_List_Music;


public class ViewPagerPlayMusic extends FragmentStateAdapter {

    public ViewPagerPlayMusic(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_PLay_List_Music();
            case 1:
                return new Fragment_Music_Dish();
            default:
                return new Fragment_PLay_List_Music();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
