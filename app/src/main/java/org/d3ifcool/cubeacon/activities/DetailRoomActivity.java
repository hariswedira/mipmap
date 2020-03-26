package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.Room;

public class DetailRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        Room room = getIntent().getParcelableExtra("detail room");
        Toast.makeText(this, room.getName(), Toast.LENGTH_SHORT).show();
    }
}
