package com.example.mapcovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class History extends AppCompatActivity {

    String s1[], s2[];
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recycler_view = findViewById(R.id.recycler_view);

        s1 = getResources().getStringArray(R.array.locations);
        s2 = getResources().getStringArray(R.array.days);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2);
        recycler_view.setAdapter(myAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }
}