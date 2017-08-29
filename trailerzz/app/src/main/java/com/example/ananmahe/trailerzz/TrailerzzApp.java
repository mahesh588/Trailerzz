package com.example.ananmahe.trailerzz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TrailerzzApp extends YouTubeBaseActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailerzz_app);
        mRecyclerView = (RecyclerView) findViewById(R.id.youtubeDataRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        TrailerzzApp.context = getApplicationContext();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new YouTubeDataAdapter(mRecyclerView);
    }

    public static Context getAppContext() {
        return TrailerzzApp.context;
    }
}




