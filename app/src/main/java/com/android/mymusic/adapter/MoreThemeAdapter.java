package com.android.mymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.activity.CategoryWithThemeActivity;
import com.android.mymusic.model.Theme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoreThemeAdapter extends RecyclerView.Adapter<MoreThemeAdapter.ViewHolder>{

    Context context;
    ArrayList<Theme> themeArrayList;

    public MoreThemeAdapter(Context context, ArrayList<Theme> themeArrayList) {
        this.context = context;
        this.themeArrayList = themeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.more_theme_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Theme theme = themeArrayList.get(position);
        Picasso.with(context).load(theme.getThemeImage()).into(holder.imageViewMoreTheme);
    }

    @Override
    public int getItemCount() {
        return themeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewMoreTheme;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMoreTheme = itemView.findViewById(R.id.imageViewMoreThemeLine);
            imageViewMoreTheme.setOnClickListener(view -> {
                Intent intent = new Intent(context, CategoryWithThemeActivity.class);
                intent.putExtra("theme",themeArrayList.get(getPosition()));
                context.startActivity(intent);
            });
        }
    }
}
