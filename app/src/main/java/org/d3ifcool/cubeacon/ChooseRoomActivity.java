package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class ChooseRoomActivity extends AppCompatActivity {

    private SearchView searchRoom;
    private ListView listRoom;

    private ArrayList<String> rooms;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_room);

        searchRoom = findViewById(R.id.search_rooms);
        listRoom = findViewById(R.id.list_room);

        rooms = new ArrayList<>();
        addRoom();
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,rooms);
        listRoom.setAdapter(arrayAdapter);

        searchRoom.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

    }

    private void addRoom() {
        rooms.add("MP Mart");
        for (int i = 0; i < 12 ; i++) {
            rooms.add("G"+(i+1));
        }
    }
}
