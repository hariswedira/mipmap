package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.Room;

public class DetailRoomActivity extends AppCompatActivity {

    ImageView photo, backArrow;
    TextView title, desc, floor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        photo = findViewById(R.id.iv_room_image);
        title = findViewById(R.id.tv_room_detail_title);
        desc = findViewById(R.id.tv_room_detail_desc);
        floor = findViewById(R.id.tv_room_detail_floor);
        backArrow = findViewById(R.id.iv_arrow_detail_room);

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

    }
}
