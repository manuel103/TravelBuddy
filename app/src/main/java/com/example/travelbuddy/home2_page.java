package com.example.travelbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class home2_page extends AppCompatActivity {

    private static final String TAG = "home2_page";
    private static final int NUM_COLUMNS = 2;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> MimageUrls = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2_page);

        initImageBitmaps();
    }


    public void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");
        MimageUrls.add("https://images.app.goo.gl/FQfdXRQHKFiSn6pd8");
        mNames.add("MaasaiMara");
        MimageUrls.add("https://images.app.goo.gl/5WLNGwPpf7RPLJsU8");
        mNames.add("Mt.Kenya");
        MimageUrls.add("https://images.app.goo.gl/aNMdcCJEUZ6ZMK5j8");
        mNames.add("Mombasa");
        MimageUrls.add("https://images.app.goo.gl/2XtD9w6SRaRP2Fpi9");
        mNames.add("Coast");
        MimageUrls.add("https://images.app.goo.gl/8B3YgdK9quzSU92w5");
        mNames.add("Kenya");
        MimageUrls.add("https://images.app.goo.gl/pjvGSKVRK3BuA8SCA");
        mNames.add("MaasaiMara");
        MimageUrls.add("https://images.app.goo.gl/BqFE52GmmvDQxMUk8");
        mNames.add("amboseli");
    }

    private  void  initRecyclerView(){
        Log.d(TAG, "initRecyclerView: initializing staggered recyclerview.");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        home2_adapter home_adapter = new home2_adapter( context, mNames, MimageUrls);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(home_adapter);



    }
}
