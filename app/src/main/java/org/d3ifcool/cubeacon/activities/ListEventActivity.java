package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.d3ifcool.cubeacon.EventActivity;
import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.adapter.EventAdapter;
import org.d3ifcool.cubeacon.models.Event;

import java.util.ArrayList;

public class ListEventActivity extends AppCompatActivity {

    private RecyclerView rvEvent;
    private ArrayList<Event> listEvent;
    private EventAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private TextView appbar;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        EventActivity eventActivity = new EventActivity();
        int num = getIntent().getIntExtra(eventActivity.EVENT_ID, 0);

        backArrow = findViewById(R.id.iv_arrow_list_event);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mdi_arrow_back.png?alt=media&token=232eaafa-e295-4df3-a575-33cac8f010e7").into(backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        appbar = findViewById(R.id.tv_list_event_appbar);
        rvEvent = findViewById(R.id.list_events);
        linearLayoutManager = new LinearLayoutManager(ListEventActivity.this);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(rvEvent.getContext(),
                linearLayoutManager.getOrientation());
        rvEvent.addItemDecoration(dividerItemDecoration);
        adapter = new EventAdapter(ListEventActivity.this);
        listEvent = new ArrayList<>();
        initData(num);
        adapter.setEvents(listEvent);
        rvEvent.setLayoutManager(linearLayoutManager);
        rvEvent.setAdapter(adapter);
    }

    private void initData(int num) {
        if (num == 2) {
            appbar.setText("Bluberry Events");
            Event events = new Event();
            events.setRoom("G9");
            events.setTitle("Workshop");
            events.setContent("Android JetPack");
            events.setDate("20 Juni 2020");
            events.setPoster("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/just_logo.png?alt=media&token=5db11fe4-0691-4cf4-883b-e8f71bbb948a");
            events.setOragnizer("Laboran FIT");
            listEvent.add(events);
        }else if (num == 3) {
            appbar.setText("Coconut Events");
            Event events = new Event();
            events.setRoom("MP Mart");
            events.setTitle("Discoun 50%");
            events.setContent("All Item Discount");
            events.setDate("20 mei 2020");
            events.setPoster("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/just_logo.png?alt=media&token=5db11fe4-0691-4cf4-883b-e8f71bbb948a");
            events.setOragnizer("Manajemen Pemasaran");
            listEvent.add(events);
        }else if (num == 1) {
            appbar.setText("Mint Events");
            Event events = new Event();
            events.setRoom("Kitchen");
            events.setTitle("Free cake");
            events.setContent("Apple pie free");
            events.setDate("20 April 2020");
            events.setPoster("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/just_logo.png?alt=media&token=5db11fe4-0691-4cf4-883b-e8f71bbb948a");
            events.setOragnizer("Perhotelan");
            listEvent.add(events);
        }else if (num == 4) {
            appbar.setText("Ice Events");
            Event events = new Event();
            events.setRoom("Lobby");
            events.setTitle("Pameran Lab");
            events.setContent("Lab IT di Telkom");
            events.setDate("23 Aoril 2020");
            events.setPoster("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/just_logo.png?alt=media&token=5db11fe4-0691-4cf4-883b-e8f71bbb948a");
            events.setOragnizer("D3 Informatika");
            listEvent.add(events);
        }
    }
}
