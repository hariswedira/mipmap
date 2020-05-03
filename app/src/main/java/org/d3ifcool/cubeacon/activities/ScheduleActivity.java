package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.adapter.PageAdapter;
import org.d3ifcool.cubeacon.fragments.JumatFragment;
import org.d3ifcool.cubeacon.fragments.KamisFragment;
import org.d3ifcool.cubeacon.fragments.RabuFragment;
import org.d3ifcool.cubeacon.fragments.SabtuFragment;
import org.d3ifcool.cubeacon.fragments.SelasaFragment;
import org.d3ifcool.cubeacon.fragments.SeninFragment;
import org.d3ifcool.cubeacon.models.Schedule;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView backArrow;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dayCur = database.getReference("currentDay");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        tabLayout = findViewById(R.id.tab_schedule);
        viewPager = findViewById(R.id.pager_list_schedule);
        backArrow = findViewById(R.id.iv_arrow_schedule);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mdi_arrow_back.png?alt=media&token=232eaafa-e295-4df3-a575-33cac8f010e7").into(backArrow);

//        SeninFragment seninFragment = new SeninFragment();
//        seninFragment.setListSchedule(listSchedule);

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SeninFragment(),"Senin");
        adapter.addFragment(new SelasaFragment(),"Selasa");
        adapter.addFragment(new RabuFragment(),"Rabu");
        adapter.addFragment(new KamisFragment(),"Kamis");
        adapter.addFragment(new JumatFragment(),"Jum'at");
        adapter.addFragment(new SabtuFragment(),"Sabtu");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
