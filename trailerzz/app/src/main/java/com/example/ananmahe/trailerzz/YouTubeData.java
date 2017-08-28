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

    public YouTubeData(String title, String releaseDate, String videoId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.videoId = videoId;
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

}
