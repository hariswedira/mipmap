package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        EventActivity eventActivity = new EventActivity();
        int num = getIntent().getIntExtra(eventActivity.EVENT_ID, 0);

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
            events.setPoster(R.drawable.icon_mipmap);
            events.setOragnizer("Laboran FIT");
            listEvent.add(events);
        } else if (num == 3) {
            appbar.setText("Coconut Events");
            Event events = new Event();
            events.setRoom("MP Mart");
            events.setTitle("Discoun 50%");
            events.setContent("All Item Discount");
            events.setDate("20 mei 2020");
            events.setPoster(R.drawable.icon_mipmap);
            events.setOragnizer("Manajemen Pemasaran");
            listEvent.add(events);
        }
    }
}
