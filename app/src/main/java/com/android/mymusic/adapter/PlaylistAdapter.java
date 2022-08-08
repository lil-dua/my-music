package com.android.mymusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.mymusic.R;
import com.android.mymusic.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        TextView txtPlaylistName;
        ImageView imageViewIcon,imageViewBackground;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.playlist_line,null);
            viewHolder = new ViewHolder();
            viewHolder.txtPlaylistName = convertView.findViewById(R.id.textViewPlaylistName);
            viewHolder.imageViewIcon = convertView.findViewById(R.id.imageViewIconPlaylist);
            viewHolder.imageViewBackground = convertView.findViewById(R.id.imageButtonBackgroundPlaylist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getPlaylistIcon()).into(viewHolder.imageViewIcon);
        Picasso.with(getContext()).load(playlist.getPlaylistImage()).into(viewHolder.imageViewBackground);
        viewHolder.txtPlaylistName.setText(playlist.getPlaylistName());
        return convertView;
    }
}
