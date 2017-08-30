package com.example.ananmahe.trailerzz;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by ananmahe on 8/21/17.
 */

public class YoutubeDataAPI {
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtubeData;
    private static final String CHANNEL_ID = "UCkR0GY0ue02aMyM-oxwgg9g";
    private static YouTube.Search.List search;
    private IYoutubeDataAPIListener mListener;
    private List<YouTubeData> youtubeDataList = new ArrayList<YouTubeData>();

    private static YoutubeDataAPI mObject = null;

    public static YoutubeDataAPI getInstance() {
        if (mObject == null) {
            mObject = new YoutubeDataAPI();
        }
        return mObject;
    }

    public void setListener(IYoutubeDataAPIListener mListener) {
        this.mListener = mListener;
    }

    private YoutubeDataAPI(){
        try {

            youtubeData = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {

                }
            }).setApplicationName("youtube-data-api").build();

            ResourceId resourceId = new ResourceId();
            resourceId.setChannelId(CHANNEL_ID);
            resourceId.setKind("youtube#channel");

            // Define the API request for retrieving search results.
            search = youtubeData.search().list("id, snippet");

            search.setKey(DeveloperKey.DEVELOPER_KEY);
            search.setChannelId(CHANNEL_ID);
            search.setType("video");
            search.setOrder("date");
            search.setFields("items(id/videoId,snippet/title,snippet/publishedAt,snippet/thumbnails/medium/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            System.out.println("============: Channel id: " + search.getChannelId());
            fetchDataFromAPI();
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void setVideoAsWatched(int index) {
        if (youtubeDataList.get(index).isWatched() == false) {
            youtubeDataList.get(index).setVideoAsWatched();
            refreshListeners();
        }
    }

    public void refreshListeners() {
        // create a handler to post messages to the main thread
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("######## Sending response");
                mListener.refreshData(youtubeDataList);
            }
        });
    }


    public void fetchDataFromAPI() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    final SearchListResponse searchResponse;
                    searchResponse = search.execute();
                    List<SearchResult> searchResultList = searchResponse.getItems();
                    System.out.println("######## Res: "+searchResultList.toString());

                    if (searchResultList != null) {
                        Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
                        if (!iteratorSearchResults.hasNext()) {
                            System.out.println("There aren't any results for your query.");
                        }
                        while (iteratorSearchResults.hasNext()) {
                            SearchResult singleVideo = iteratorSearchResults.next();
                            ResourceId rId = singleVideo.getId();
                            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");

                            youtubeDataList.add(new YouTubeData(singleVideo.getSnippet().getTitle(),
                                    sdf.format(new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss").parse(singleVideo.getSnippet().getPublishedAt().toString())).toString(),
                                    rId.getVideoId(),singleVideo.getSnippet().getThumbnails().getMedium().getUrl()));
                        }
                    }

                    refreshListeners();

                } catch (IOException e) {
                    System.err.println("######## There was an IO error: " + e.getCause() + " : " + e.getMessage());
                } catch (Throwable t) {
                    System.err.println("######## ");
                    t.printStackTrace();
                }
            }
        });

    }

    interface IYoutubeDataAPIListener {
        public void refreshData(List<YouTubeData> youTubeDataList);
    }

}
