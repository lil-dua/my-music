package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.mymusic.R;
import com.android.mymusic.adapter.CategoryWithThemeAdapter;
import com.android.mymusic.model.Category;
import com.android.mymusic.model.Theme;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryWithThemeActivity extends AppCompatActivity {

    Theme theme;
    RecyclerView recyclerViewCategoryWithTheme;
    Toolbar toolbarCategoryWithTheme;
    CategoryWithThemeAdapter categoryWithThemeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_with_theme);
        GetIntent();
        Mapping();
        Init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Category>> callback = dataservice.GetCategoryWithTheme(theme.getIdTheme());
        callback.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                ArrayList<Category> categoryArrayList = (ArrayList<Category>) response.body();
                categoryWithThemeAdapter = new CategoryWithThemeAdapter(CategoryWithThemeActivity.this,categoryArrayList);
                recyclerViewCategoryWithTheme.setLayoutManager(new GridLayoutManager(CategoryWithThemeActivity.this,2));
                recyclerViewCategoryWithTheme.setAdapter(categoryWithThemeAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra("theme")){
            theme = (Theme) intent.getSerializableExtra("theme");
        }
    }

    private void Mapping() {
        recyclerViewCategoryWithTheme = findViewById(R.id.recycleViewCategoryWithTheme);
        toolbarCategoryWithTheme = findViewById(R.id.toolbarCategoryWithTheme);
    }

    private void Init() {
        setSupportActionBar(toolbarCategoryWithTheme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(theme.getThemeName());
        toolbarCategoryWithTheme.setTitleTextColor(getResources().getColor(R.color.yellow));
        toolbarCategoryWithTheme.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}