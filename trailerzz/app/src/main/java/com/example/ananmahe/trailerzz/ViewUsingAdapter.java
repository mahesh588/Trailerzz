package com.example.ananmahe.trailerzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class ViewUsingAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_using_adapter);
        String [] texts = {"MKBHD", "chris angel", "monte carlo", "luxor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.adapter_text_view, texts);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);
    }
}
