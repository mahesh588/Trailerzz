package com.example.ananmahe.trailerzz;

import android.net.Uri;

import java.net.URI;

/**
 * Created by ananmahe on 8/18/17.
 */

public class YouTubeData {

    protected String title;
    protected String releaseDate;
    protected String videoId;
    protected String thumbnailUrl;
    protected  Boolean watched = false;

    public YouTubeData(String title, String releaseDate, String videoId, String thumbnailUrl) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.watched = false;
    }

    public String getTitle() {
        return this.title;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public Boolean isWatched() {
        return this.watched;
    }

    public void setVideoAsWatched() {
        this.watched = true;
    }

}
