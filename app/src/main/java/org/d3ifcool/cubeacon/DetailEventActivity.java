package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.d3ifcool.cubeacon.models.Event;

public class DetailEventActivity extends AppCompatActivity {

    ImageView photo, backArrow;
    TextView judul, tgl, ruangan, deskripsi, organizer;
    Button see;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        photo = findViewById(R.id.iv_detail_room);
        judul = findViewById(R.id.tv_detail_judul);
        tgl = findViewById(R.id.tv_detail_tanggal);
        ruangan = findViewById(R.id.tv_detail_ruangan);
        deskripsi = findViewById(R.id.tv_detail_desc);
        organizer = findViewById(R.id.tv_organizer_detail);
        backArrow = findViewById(R.id.iv_arrow_event);
        see = findViewById(R.id.btn_see_event);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mdi_arrow_back.png?alt=media&token=232eaafa-e295-4df3-a575-33cac8f010e7").into(backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.instagram.com/p/BuDwrKVAeON/?igshid=jgqt58uyjad8");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        Event event = getIntent().getParcelableExtra("event");

//        int poster = intent.getIntExtra("photo",R.drawable.ic_launcher_background);
//        String title = intent.getStringExtra("title");
//        String date = intent.getStringExtra("date");
//        String room = intent.getStringExtra("room");
//        String desc = intent.getStringExtra("desc");

        assert event != null;
//        photo.setImageResource(event.getPoster());
        Glide.with(this).load(event.getPhoto()).into(photo);
        judul.setText(event.getTitle());
        tgl.setText(event.getDate());
        ruangan.setText("by "+event.getRoom());
        deskripsi.setText(event.getContent());
        organizer.setText("at "+event.getOragnizer());
    }
}
