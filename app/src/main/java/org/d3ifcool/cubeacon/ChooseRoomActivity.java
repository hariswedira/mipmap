package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import org.d3ifcool.cubeacon.activities.ListEventActivity;
import org.d3ifcool.cubeacon.adapter.EventAdapter;
import org.d3ifcool.cubeacon.adapter.RoomAdapter;
import org.d3ifcool.cubeacon.models.Room;
import org.d3ifcool.cubeacon.utils.Constants;
import org.d3ifcool.cubeacon.utils.Preferences;

import java.util.ArrayList;

public class ChooseRoomActivity extends AppCompatActivity {

    private SearchView searchRoom;
    private RecyclerView rVListRoom;
    private ArrayList<Room> listRooms;
    private RoomAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private Button classRoom, lab, all;
    private int userPos;
//    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_room);

        userPos = getIntent().getIntExtra("user pos",100);

        classRoom = findViewById(R.id.btn_classroom);
        lab = findViewById(R.id.btn_laboratory);
        all = findViewById(R.id.btn_all_room);

        searchRoom = findViewById(R.id.search_rooms);
        rVListRoom = findViewById(R.id.list_room);

        linearLayoutManager = new LinearLayoutManager(ChooseRoomActivity.this);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(rVListRoom.getContext(),
                linearLayoutManager.getOrientation());
        rVListRoom.addItemDecoration(dividerItemDecoration);
        adapter = new RoomAdapter(ChooseRoomActivity.this,userPos);
        listRooms = new ArrayList<>();
        initData();
        adapter.setRooms(listRooms);
        rVListRoom.setLayoutManager(linearLayoutManager);
        rVListRoom.setAdapter(adapter);
//        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,rooms);
//        listRoom.setAdapter(arrayAdapter);

        searchRoom.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                arrayAdapter.getFilter().filter(s);
                String userInput = s.toLowerCase();
                ArrayList<Room> newList = new ArrayList<>();
                for (Room room : listRooms){
                    if (room.getName().toLowerCase().contains(userInput)){
                        newList.add(room);
                    }
                }
                adapter.updateList(newList);
                return true;
            }
        });

        classRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Room> newList = new ArrayList<>();
                for (int i = 0; i <= 11 ; i++) {
                    newList.add(new Room("G"+(i+1),getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                }
                adapter.updateList(newList);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.updateList(listRooms);
            }
        });

        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Room> newList = new ArrayList<>();
                newList.add(new Room("Admin Lab","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                newList.add(new Room("Kitchen","Laboratorium","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                newList.add(new Room("Laboran","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                newList.add(new Room("LAK","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                newList.add(new Room("MP Mart","Mini Market","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                newList.add(new Room("Toilet","Rest Room","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                adapter.updateList(newList);
            }
        });

    }

    private void initData() {
        listRooms.add(new Room("Admin Lab","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Kitchen","Laboratorium","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Laboran","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("LAK","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("MP Mart","Mini Market","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Toilet","Rest Room","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Kantin","Cafetaria","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Lobby","lobby fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Lift","lift fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Gate","Gate fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Exit","Exit fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Dosen LB","Dosen Luar Biasa","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        for (int i = 0; i <= 11 ; i++) {
            listRooms.add(new Room("G"+(i+1),getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        }
    }
}
