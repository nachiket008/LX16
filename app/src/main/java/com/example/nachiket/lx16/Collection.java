package com.example.nachiket.lx16;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Ankit on 14-01-2016.
 */
public class Collection extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        recyclerView1 =(RecyclerView) findViewById(R.id.recycleView1);
        linearLayoutManager = new LinearLayoutManager(this);


        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(new RecycleImageAdapter_collection(this));

    }}
