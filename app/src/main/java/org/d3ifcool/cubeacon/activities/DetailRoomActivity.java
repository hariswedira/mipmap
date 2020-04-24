package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.Room;

public class DetailRoomActivity extends AppCompatActivity {

    ImageView photo, backArrow;
    TextView title, desc, floor, type, supervisor, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        photo = findViewById(R.id.iv_room_image);
        title = findViewById(R.id.tv_room_detail_title);
        desc = findViewById(R.id.tv_room_detail_desc);
        type = findViewById(R.id.tv_detail_type);
        supervisor = findViewById(R.id.tv_detail_supervisor);
        number = findViewById(R.id.tv_detail_supervisor_number);
        floor = findViewById(R.id.tv_room_detail_floor);

        backArrow = findViewById(R.id.iv_arrow_detail_room);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mdi_arrow_back.png?alt=media&token=232eaafa-e295-4df3-a575-33cac8f010e7").into(backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Room room = getIntent().getParcelableExtra("detail room");
        assert room != null;
        photo.setImageResource(room.getPhoto());
        title.setText(room.getName());
        desc.setText(room.getDesc());
        floor.setText(room.getFloor());
        supervisor.setText(room.getSupervisor());
        type.setText(room.getType());
        number.setText(room.getNumber());
    }
}
