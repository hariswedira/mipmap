package org.d3ifcool.cubeacon.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.adapter.PageAdapter;
import org.d3ifcool.cubeacon.fragments.JumatFragment;
import org.d3ifcool.cubeacon.fragments.KamisFragment;
import org.d3ifcool.cubeacon.fragments.RabuFragment;
import org.d3ifcool.cubeacon.fragments.SabtuFragment;
import org.d3ifcool.cubeacon.fragments.SelasaFragment;
import org.d3ifcool.cubeacon.fragments.SeninFragment;
import org.d3ifcool.cubeacon.models.Schedule;
import org.d3ifcool.cubeacon.utils.Constants;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView backArrow;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dayCur = database.getReference("currentDay");

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        SeninFragment seninFragment = new SeninFragment();
        SelasaFragment selasaFragment = new SelasaFragment();
        RabuFragment rabuFragment = new RabuFragment();
        KamisFragment kamisFragment = new KamisFragment();
        JumatFragment jumatFragment = new JumatFragment();
        SabtuFragment sabtuFragment = new SabtuFragment();

        String titleRoom = getIntent().getStringExtra(Constants.ROOM) ;
        seninFragment.setTitle(titleRoom);
        selasaFragment.setTitle(titleRoom);
        rabuFragment.setTitle(titleRoom);
        kamisFragment.setTitle(titleRoom);
        jumatFragment.setTitle(titleRoom);
        sabtuFragment.setTitle(titleRoom);

        tabLayout = findViewById(R.id.tab_schedule);
        viewPager = findViewById(R.id.pager_list_schedule);
        backArrow = findViewById(R.id.iv_arrow_schedule);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mdi_arrow_back.png?alt=media&token=232eaafa-e295-4df3-a575-33cac8f010e7")
                .placeholder(R.drawable.image_placeholder)
                .into(backArrow);

        dayCur.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 setValue(dataSnapshot.getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        SeninFragment seninFragment = new SeninFragment();
//        seninFragment.setListSchedule(listSchedule);

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(seninFragment,"Monday");
        adapter.addFragment(selasaFragment,"Tuesday");
        adapter.addFragment(rabuFragment,"Wednesday");
        adapter.addFragment(kamisFragment,"Thursday");
        adapter.addFragment(jumatFragment,"Friday");
        adapter.addFragment(sabtuFragment,"Saturday");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getValue());
        tabLayout.setupWithViewPager(viewPager);

    }
}
