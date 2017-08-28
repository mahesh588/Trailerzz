package com.example.ananmahe.trailerzz;

import android.os.AsyncTask;
import android.os.Handler;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;


/**
 * Created by ananmahe on 8/21/17.
 */

public class YoutubeDataAPI {
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtubeData;
    private static final String CHANNEL_ID = "UCkR0GY0ue02aMyM-oxwgg9g";
    private static YouTube.Search.List search;
    private YoutubeDataAPIListener mListener;

    public YoutubeDataAPI(YoutubeDataAPIListener mListener){
        try {
            this.mListener = mListener;
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
            search.setFields("items(id/videoId)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            System.out.println("============: Channel id: " + search.getChannelId());
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public void getData() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("######## InRun");
                try {
                    final SearchListResponse searchResponse;
                    searchResponse = search.execute();
                    System.out.println("######## Res: "+searchResponse.toString());
                    // create a handler to post messages to the main thread
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.setResponse(searchResponse.toString());
                        }
                    });
                } catch (IOException e) {
                    System.err.println("######## There was an IO error: " + e.getCause() + " : " + e.getMessage());
                } catch (Throwable t) {
                    System.err.println("######## ");
                    t.printStackTrace();
                }
            }
        });

    }

    interface 
}

class YoutubeDataAPIListener {
    private static YoutubeDataAPIListener mListener = null;
    private String response = "";

    private YoutubeDataAPIListener() {

    }

    public static YoutubeDataAPIListener getListener() {
        if (mListener == null) {
            mListener = new YoutubeDataAPIListener();
        }
        return mListener;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void responseRecieved(String response) {
        System.out.println("============ Result: " + response);
        this.response = response;
    }

}
