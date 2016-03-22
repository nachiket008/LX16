package com.example.nachiket.lx16;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Database extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
    recyclerView =(RecyclerView) findViewById(R.id.recycleView);
    linearLayoutManager = new LinearLayoutManager(this);


    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(new RecycleImageAdapter(this));



    }
}
