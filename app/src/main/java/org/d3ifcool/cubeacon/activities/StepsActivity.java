package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.adapter.StepAdapter;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {

    private ArrayList<String> list;
    private StepAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        list = getIntent().getStringArrayListExtra("steps");

        recyclerView = findViewById(R.id.steps);
        linearLayoutManager = new LinearLayoutManager(StepsActivity.this);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new StepAdapter(StepsActivity.this);

        adapter.setList(list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
