package com.example.ananmahe.trailerzz;

import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ananmahe on 8/15/17.
 */

public class YouTubeDataAdapter extends RecyclerView.Adapter<YouTubeDataAdapter.YoutubeDataViewHolder> implements YoutubeDataAPI.IYoutubeDataAPIListener {

    private List<YouTubeData> youtubeDataList;
    private YoutubeDataAPI youtubeDataAPI;
    private RecyclerView mRecyclerview;

    public YouTubeDataAdapter(RecyclerView mRecyclerview) {
        youtubeDataAPI = new YoutubeDataAPI(this);
        this.mRecyclerview = mRecyclerview;
        youtubeDataAPI.getData();
    }

    public static class YoutubeDataViewHolder extends RecyclerView.ViewHolder {
        protected TextView vMovieTitle;
        protected TextView vReleaseDate;
        protected ImageView vMovieThumbnail;

        public YoutubeDataViewHolder(View v) {
            super(v);
            vMovieTitle = (TextView) v.findViewById(R.id.movie_title);
            vReleaseDate = (TextView) v.findViewById(R.id.movie_release_date);
            //vMovieTrailer = (YouTubePlayerView) v.findViewById(R.id.movie_trailer);
            vMovieThumbnail = (ImageView) v.findViewById(R.id.movie_image);
        }
    }

    public void responseRecieved(List<YouTubeData> youtubeDataList) {
        this.youtubeDataList = youtubeDataList;
        System.out.println("######### Response recieved");
        mRecyclerview.setAdapter(this);
    }

    @Override
    public void onBindViewHolder(final YoutubeDataViewHolder youtubeDataViewHolder, int i) {
        final YouTubeData trailerObj = youtubeDataList.get(i);
        youtubeDataViewHolder.vMovieTitle.setText(trailerObj.getTitle());
        youtubeDataViewHolder.vReleaseDate.setText(trailerObj.getReleaseDate());
        Picasso.with(TrailerzzApp.getAppContext()).load(trailerObj.getThumbnailUrl()).into(youtubeDataViewHolder.vMovieThumbnail);

    }

    @Override
    public YoutubeDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View cardView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youtube_card_view, viewGroup, false);
        return new YoutubeDataViewHolder(cardView);
    }

    @Override
    public int getItemCount() {
        return youtubeDataList.size();
    }
}


