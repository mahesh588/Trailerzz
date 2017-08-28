package com.example.ananmahe.trailerzz;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by ananmahe on 8/15/17.
 */

public class YouTubeDataAdapter extends RecyclerView.Adapter<YouTubeDataAdapter.YoutubeDataViewHolder> {

    private List<YouTubeData> youtubeDataList;
    private YoutubeDataAPIListener mListener;
    private YoutubeDataAPI youtubeDataAPI;

    public YouTubeDataAdapter(List<YouTubeData> youtubeDataList) {
        this.youtubeDataList = youtubeDataList;
        this.mListener = YoutubeDataAPIListener.getListener();
        youtubeDataAPI = new YoutubeDataAPI(this);
        youtubeDataAPI.getData();
    }

    public static class YoutubeDataViewHolder extends RecyclerView.ViewHolder {
        protected TextView vMovieTitle;
        protected TextView vReleaseDate;
        protected YouTubePlayerView vMovieTrailer;
        protected YouTubeThumbnailView vMovieThumbnail;

        public YoutubeDataViewHolder(View v) {
            super(v);
            vMovieTitle = (TextView) v.findViewById(R.id.movie_title);
            vReleaseDate = (TextView) v.findViewById(R.id.movie_release_date);
            vMovieTrailer = (YouTubePlayerView) v.findViewById(R.id.movie_trailer);
            vMovieThumbnail = (YouTubeThumbnailView) v.findViewById(R.id.movie_thumbnail);
        }
    }

    @Override
    public void onBindViewHolder(final YoutubeDataViewHolder youtubeDataViewHolder, int i) {
        final YouTubeData trailerObj = youtubeDataList.get(i);
        youtubeDataViewHolder.vMovieTitle.setText(trailerObj.getTitle());
        youtubeDataViewHolder.vReleaseDate.setText(trailerObj.getReleaseDate());
        youtubeDataViewHolder.vMovieTrailer.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.loadVideo(trailerObj.getVideoId());
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                System.out.println(error);
            }
        });

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                //youTubeThumbnailView.setVisibility(View.VISIBLE);
                //youtubeDataViewHolder.vMovieTrailer.setVisibility(View.VISIBLE);
            }
        };

        youtubeDataViewHolder.vMovieThumbnail.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(trailerObj.getVideoId());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
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


