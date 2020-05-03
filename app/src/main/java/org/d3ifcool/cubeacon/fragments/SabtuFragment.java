package org.d3ifcool.cubeacon.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.adapter.ScheduleAdapter;
import org.d3ifcool.cubeacon.models.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SabtuFragment extends Fragment {

    private ArrayList<Schedule> listSchedule;
    private ArrayList<Schedule> listOnGoing;
    private RecyclerView rvSchedule;
    private RecyclerView rvOnGoing;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;
    private ScheduleAdapter adapter;
    private TextView onGoing;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dayCur = database.getReference("currentDay");


    public SabtuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sabtu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listSchedule = new ArrayList<>();
        listOnGoing = new ArrayList<>();
        initDataSchedule();

        rvSchedule = view.findViewById(R.id.rv_list_sabtu);
        rvOnGoing = view.findViewById(R.id.rv_list_on_sabtu);
        onGoing = view.findViewById(R.id.tv_ongoing_sabtu);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager2 = new LinearLayoutManager(getContext());

        dayCur.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                if (value == 7){

                    for (int i = 0; i < listSchedule.size() ; i++) {

                        String pre = listSchedule.get(i).getTime().substring(0,5);
                        String post = listSchedule.get(i).getTime().substring(8,14);
                        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

                        try {
//            Date userDate = parser.parse("08:15");
                            Date date = new Date();
                            String timeNow = parser.format(date);
                            Date userDate = parser.parse(timeNow);
                            Date start = parser.parse(pre);
                            Date end = parser.parse(post);
                            if (userDate.after(start) && userDate.before(end)) {
                                // change status
//                    Toast.makeText(getContext(), "Uhuy", Toast.LENGTH_SHORT).show();
                                listSchedule.get(i).setIsGoing(0);
                                listOnGoing.add(listSchedule.get(i));
                                listSchedule.remove(i);
                            }else {
//                    Toast.makeText(getContext(), "No "+timeNow, Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e) {
                            // Invalid date was entered
                        }

                    }

                    adapter = new ScheduleAdapter(getContext());
                    adapter.setSchedules(listOnGoing);
                    rvOnGoing.setLayoutManager(linearLayoutManager2);
                    rvOnGoing.setAdapter(adapter);

                }else {
                    rvOnGoing.setVisibility(View.GONE);
                    onGoing.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ScheduleAdapter(getContext());
        adapter.setSchedules(listSchedule);
        rvSchedule.setLayoutManager(linearLayoutManager);
        rvSchedule.setAdapter(adapter);
    }

    private void initDataSchedule(){
        listSchedule.add(new Schedule("Pemograman berbasis sensor","18:00 - 19:30 WIB","Icih Caroline",1));
        listSchedule.add(new Schedule("Pemograman berbasis sensor","08:30 - 12:30 WIB","Icih Caroline",1));
        listSchedule.add(new Schedule("Pemograman berbasis sensor","08:30 - 12:30 WIB","Icih Caroline",1));
        listSchedule.add(new Schedule("Pemograman berbasis sensor","08:30 - 12:30 WIB","Icih Caroline",1));
        listSchedule.add(new Schedule("Pemograman berbasis sensor","08:30 - 12:30 WIB","Icih Caroline",1));
    }

}
