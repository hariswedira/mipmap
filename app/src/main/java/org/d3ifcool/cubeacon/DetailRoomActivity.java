package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailRoomActivity extends AppCompatActivity {

    ImageView photo;
    TextView judul, tgl, ruangan, deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        Intent intent = getIntent();

        photo = findViewById(R.id.iv_detail_room);
        judul = findViewById(R.id.tv_detail_judul);
        tgl = findViewById(R.id.tv_detail_tanggal);
        ruangan = findViewById(R.id.tv_detail_ruangan);
        deskripsi = findViewById(R.id.tv_detail_desc);

        int poster = intent.getIntExtra("photo",R.drawable.ic_launcher_background);
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String room = intent.getStringExtra("room");
        String desc = intent.getStringExtra("desc");

        photo.setImageResource(poster);
        judul.setText(title);
        tgl.setText(date);
        ruangan.setText(room);
        deskripsi.setText(desc);
    }
}
