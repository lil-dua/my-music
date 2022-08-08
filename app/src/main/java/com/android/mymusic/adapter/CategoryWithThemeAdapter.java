package com.android.mymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.activity.ListSongsActivity;
import com.android.mymusic.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CategoryWithThemeAdapter extends RecyclerView.Adapter<CategoryWithThemeAdapter.ViewHolder>{

    Context context;
    ArrayList<Category> categoryArrayList;

    public CategoryWithThemeAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_with_theme_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        Picasso.with(context).load(category.getCategoryImage()).into(holder.imageViewCategoryWithTheme);
        holder.txtNameCategoryWithTheme.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewCategoryWithTheme;
        TextView txtNameCategoryWithTheme;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategoryWithTheme = itemView.findViewById(R.id.imageViewCategoryWithTheme);
            txtNameCategoryWithTheme = itemView.findViewById(R.id.textViewNameCategoryWithTheme);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListSongsActivity.class);
                    intent.putExtra("idCategory",categoryArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
