package com.example.ananmahe.trailerzz;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ananmahe on 8/15/17.
 */

class YoutubeDataViewHolder extends RecyclerView.ViewHolder {
    protected TextView vMovieTitle;
    protected TextView vReleaseDate;
    protected ImageView vMovieThumbnail;
    protected TextView vWatched;
    private  String videoId;


    public YoutubeDataViewHolder(View itemView) {
        super(itemView);
        vMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        vReleaseDate = (TextView) itemView.findViewById(R.id.movie_release_date);
        vMovieThumbnail = (ImageView) itemView.findViewById(R.id.movie_image);
        vWatched = (TextView) itemView.findViewById(R.id.movie_watched_badge);
    }

    public YoutubeDataViewHolder setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public YoutubeDataViewHolder setMovieTitle(String title) {
        this.vMovieTitle.setText(title);
        return this;
    }

    public YoutubeDataViewHolder setReleaseDate(String date) {
        this.vReleaseDate.setText(date);
        return this;
    }

    public YoutubeDataViewHolder setVisibility(int visibility) {
        this.vWatched.setVisibility(visibility);
        return this;
    }

    public YoutubeDataViewHolder setThumbnail(String thumbnailUrl) {
        Picasso.with(TrailerzzApp.getAppContext()).load(thumbnailUrl).into(this.vMovieThumbnail);
        return this;
    }

    public YoutubeDataViewHolder setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }
}


public class YouTubeDataAdapter extends RecyclerView.Adapter<YoutubeDataViewHolder> implements YoutubeDataController.IDataFunctions{

    private List<YouTubeData> youtubeDataList = new ArrayList<>();
    private YoutubeDataController youtubeDataController;

    public YouTubeDataAdapter() {
        youtubeDataController = YoutubeDataController.getInstance();
        youtubeDataList = youtubeDataController.fetchData();
        EventBus.getDefault().register(this);
    }

    @Override
    public void refreshData() {
        this.youtubeDataList = youtubeDataController.fetchData();
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onYoutubeDataChangeEvent(YoutubeDataChangeEvent event) {
        refreshData();
    };


    @Override
    public void onBindViewHolder(final YoutubeDataViewHolder youtubeDataViewHolder, final int i) {
        final YouTubeData trailerObj = youtubeDataList.get(i);

        if(trailerObj.isWatched() == true) {
            youtubeDataViewHolder.setVisibility(View.VISIBLE);
        } else {
            youtubeDataViewHolder.setVisibility(View.INVISIBLE);
        }

        //Using flavour of Builder Pattern
        youtubeDataViewHolder.setMovieTitle(trailerObj.getTitle())
                             .setReleaseDate(trailerObj.getReleaseDate())
                             .setThumbnail(trailerObj.getThumbnailUrl())
                             .setVideoId(trailerObj.getVideoId())
                             .setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     Intent intent = new Intent(TrailerzzApp.getAppContext(), YoutubeFullPlayer.class);
                                     intent.putExtra("videoId", trailerObj.getVideoId());
                                     intent.putExtra("index", i);
                                     TrailerzzApp.getAppContext().startActivity(intent);
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

