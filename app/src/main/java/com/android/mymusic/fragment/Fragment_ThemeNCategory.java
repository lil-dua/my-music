package com.android.mymusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.mymusic.R;
import com.android.mymusic.activity.CategoryWithThemeActivity;
import com.android.mymusic.activity.ListSongsActivity;
import com.android.mymusic.model.Category;
import com.android.mymusic.model.CategoryTheme;
import com.android.mymusic.model.Theme;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_ThemeNCategory extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_themencategory, container, false);
        horizontalScrollView  = view.findViewById(R.id.horizontalScrollView);
        GetData();
        return view;
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<CategoryTheme> callback = dataservice.GetCategoryTheme();
        callback.enqueue(new Callback<CategoryTheme>() {
            @Override
            public void onResponse(@NonNull Call<CategoryTheme> call, @NonNull Response<CategoryTheme> response) {
                CategoryTheme categoryTheme =  response.body();

                assert categoryTheme != null;
                final ArrayList<Theme> themeArrayList = new ArrayList<>(categoryTheme.getTheme());

                final ArrayList<Category> categoryArrayList = new ArrayList<>();
                categoryArrayList.addAll(categoryTheme.getCategory());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500,500);
                layoutParams.setMargins(20,20,20,30);

                //theme array list
                for(int i = 0; i < (themeArrayList.size()) ;i++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(20);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (themeArrayList.get(i).getThemeImage() != null){
                        Picasso.with(getActivity()).load(themeArrayList.get(i).getThemeImage()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);

                    final int finalI = i;
                    imageView.setOnClickListener(view -> {
                        Intent intent = new Intent(getActivity(), CategoryWithThemeActivity.class);
                        intent.putExtra("theme", themeArrayList.get(finalI));
                        startActivity(intent);
                    });
                }
                //category array list
                for(int j = 0; j < (categoryArrayList.size()) ;j++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (categoryArrayList.get(j).getCategoryImage() != null){
                        Picasso.with(getActivity()).load(categoryArrayList.get(j).getCategoryImage()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);


                    final int finalJ = j;
                    imageView.setOnClickListener(view -> {
                        Intent intent = new Intent(getActivity(), ListSongsActivity.class);
                        intent.putExtra("idCategory",categoryArrayList.get(finalJ));
                        startActivity(intent);
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(@NonNull Call<CategoryTheme> call, @NonNull Throwable t) {

            }

        });
    }
}


