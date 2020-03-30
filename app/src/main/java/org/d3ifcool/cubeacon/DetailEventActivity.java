package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.cubeacon.models.Event;

public class DetailEventActivity extends AppCompatActivity {

    ImageView photo, backArrow;
    TextView judul, tgl, ruangan, deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

//        Intent intent = getIntent();

        photo = findViewById(R.id.iv_detail_room);
        judul = findViewById(R.id.tv_detail_judul);
        tgl = findViewById(R.id.tv_detail_tanggal);
        ruangan = findViewById(R.id.tv_detail_ruangan);
        deskripsi = findViewById(R.id.tv_detail_desc);
        backArrow = findViewById(R.id.iv_arrow_event);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Event event = getIntent().getParcelableExtra("event");

//        int poster = intent.getIntExtra("photo",R.drawable.ic_launcher_background);
//        String title = intent.getStringExtra("title");
//        String date = intent.getStringExtra("date");
//        String room = intent.getStringExtra("room");
//        String desc = intent.getStringExtra("desc");

        assert event != null;
        photo.setImageResource(event.getPoster());
        judul.setText(event.getTitle());
        tgl.setText(event.getDate());
        ruangan.setText(event.getRoom());
        deskripsi.setText(event.getContent());
    }
}
