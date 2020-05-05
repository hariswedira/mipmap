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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private Button classRoom, lab, all, riset;
    private int userPos;
//    private ArrayAdapter<String> arrayAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("userPosition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_room);

        userPos = getIntent().getIntExtra("user pos",100);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(Integer.class);
                if (value==1){
                    userPos= value;
                }else if (value==2){
                    userPos= value;
                }else if (value==3){
                    userPos= value;
                }else if (value==4){
                    userPos= value;
                }else if (value==0){
                    userPos=value;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        classRoom = findViewById(R.id.btn_classroom);
        lab = findViewById(R.id.btn_laboratory);
        all = findViewById(R.id.btn_all_room);
        riset = findViewById(R.id.btn_riset);

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
//                for (int i = 0; i <= 11 ; i++) {
//                    newList.add(new Room("G"+(i+1),getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//                }
                newList.add(new Room("G9",getResources().getString(R.string.about_g9),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
//                newList.add(new Room("G10",getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                newList.add(new Room("G6",getResources().getString(R.string.about_g6),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
                newList.add(new Room("G5",getResources().getString(R.string.about_g5),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
                newList.add(new Room("G7",getResources().getString(R.string.about_g7),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
                newList.add(new Room("Kitchen",getResources().getString(R.string.about_kitchen),"Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/kitchen.png?alt=media&token=74abd735-cf56-4f0f-ac15-20dcb20d0450", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.pexels.com/photos/1080721/pexels-photo-1080721.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"));
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
//                newList.add(new Room("Admin Lab","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//                newList.add(new Room("Kitchen","Laboratorium","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//                newList.add(new Room("Laboran","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//                newList.add(new Room("LAK","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//                newList.add(new Room("MP Mart","Mini Market","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//                newList.add(new Room("Toilet","Rest Room","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));

                newList.add(new Room("Laboran",getResources().getString(R.string.about_laboran),"Floor 1", "Pak Dodi", "08123456789", "Academic", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/lab.png?alt=media&token=ef8d115e-ad4b-461c-8817-7b395147c26d", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80"));
//                newList.add(new Room("Lobby","lobby fit","Floor 1", "Pak Dodi", "08123456789", "Academic", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
                newList.add(new Room("Kantin",getResources().getString(R.string.about_kantin),"Floor 1", "Pak Dodi", "08123456789", "Academic", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/kitchen.png?alt=media&token=74abd735-cf56-4f0f-ac15-20dcb20d0450", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1555396273-367ea4eb4db5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1567&q=80"));
                adapter.updateList(newList);
            }
        });

        riset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Room> newList = new ArrayList<>();
                newList.add(new Room("MP Mart",getResources().getString(R.string.about_mpmart),"Floor 1", "Pak Dodi", "08123456789", "research", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mp_mart.png?alt=media&token=c6660a62-03d6-4d6c-8524-57c144dbe703", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1584680226833-0d680d0a0794?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"));
                newList.add(new Room("G1",getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "research", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/lab.png?alt=media&token=ef8d115e-ad4b-461c-8817-7b395147c26d", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80"));
                adapter.updateList(newList);
            }
        });

    }

    private void initData() {
        listRooms.add(new Room("Kitchen",getResources().getString(R.string.about_kitchen),"Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/kitchen.png?alt=media&token=74abd735-cf56-4f0f-ac15-20dcb20d0450", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.pexels.com/photos/1080721/pexels-photo-1080721.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"));
        listRooms.add(new Room("MP Mart",getResources().getString(R.string.about_mpmart),"Floor 1", "Pak Dodi", "08123456789", "research", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mp_mart.png?alt=media&token=c6660a62-03d6-4d6c-8524-57c144dbe703", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1584680226833-0d680d0a0794?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"));
        listRooms.add(new Room("Kantin",getResources().getString(R.string.about_kantin),"Floor 1", "Pak Dodi", "08123456789", "Academic", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/kitchen.png?alt=media&token=74abd735-cf56-4f0f-ac15-20dcb20d0450", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1555396273-367ea4eb4db5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1567&q=80"));

        listRooms.add(new Room("G1",getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "research", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/lab.png?alt=media&token=ef8d115e-ad4b-461c-8817-7b395147c26d", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80"));
        listRooms.add(new Room("G5",getResources().getString(R.string.about_g5),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
        listRooms.add(new Room("G6",getResources().getString(R.string.about_g6),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
        listRooms.add(new Room("G7",getResources().getString(R.string.about_g7),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
        listRooms.add(new Room("G9",getResources().getString(R.string.about_g9),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/class.png?alt=media&token=d3009015-f21b-4398-bcce-a71558392d0b", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1580582932707-520aed937b7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2089&q=80"));
//        listRooms.add(new Room("G10",getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
        listRooms.add(new Room("Laboran",getResources().getString(R.string.about_laboran),"Floor 1", "Pak Dodi", "08123456789", "Academic", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/lab.png?alt=media&token=ef8d115e-ad4b-461c-8817-7b395147c26d", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088","https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80"));
//        listRooms.add(new Room("Lobby","lobby fit","Floor 1", "Pak Dodi", "08123456789", "Academic", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));

//        listRooms.add(new Room("Admin Lab","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Kitchen","Laboratorium","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Laboran","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("LAK","Academic","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("MP Mart","Mini Market","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Toilet","Rest Room","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Kantin","Cafetaria","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Lobby","lobby fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Lift","lift fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Gate","Gate fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Exit","Exit fit","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        listRooms.add(new Room("Dosen LB","Dosen Luar Biasa","Floor 1", "Pak Dodi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        for (int i = 0; i <= 11 ; i++) {
//            listRooms.add(new Room("G"+(i+1),getResources().getString(R.string.about),"Floor 1", "Amir Hasanudin Fauzi", "08123456789", "Classroom", "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/cheva.png?alt=media&token=b4b51774-2a2e-4254-bd97-98e0814f8328", "https://dif.telkomuniversity.ac.id/wp-content/uploads/2014/12/Amir-Hasanudin-Fauzi-ST.-MT-150x150.jpg","14880088"));
//        }
    }
}
