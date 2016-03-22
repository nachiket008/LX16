package com.example.nachiket.lx16;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ankit on 21-12-2015.
 */
public class About extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);
    }
}
