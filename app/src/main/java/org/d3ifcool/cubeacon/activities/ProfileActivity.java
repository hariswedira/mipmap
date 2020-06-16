package org.d3ifcool.cubeacon.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.User;
import org.d3ifcool.cubeacon.utils.Constants;
import org.d3ifcool.cubeacon.utils.Preferences;

public class ProfileActivity extends AppCompatActivity {

    EditText username, password;
    Button update;
    ImageView backarrow;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.et_username_profile);
        password = findViewById(R.id.et_password_profile);
        update = findViewById(R.id.btn_update_profile);
        backarrow = findViewById(R.id.iv_arrow_profile);

//        username.setTag(username.getKeyListener());
//        username.setKeyListener(null);
//        username.setKeyListener((KeyListener) username.getTag());

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mdi_arrow_back.png?alt=media&token=232eaafa-e295-4df3-a575-33cac8f010e7")
                .into(backarrow);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String u = Preferences.read(getApplicationContext(), Constants.USERNAME,"username");
        String p = Preferences.read(getApplicationContext(), Constants.PASSWORD,"password");

        username.setText(u);
        password.setText(p);

        myRef  = database.getReference().child("users").child(u).child("userPosition");
        final DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users");
//        final DatabaseReference username = FirebaseDatabase.getInstance().getReference().child("users").child(u).child("username");
//        final DatabaseReference password = FirebaseDatabase.getInstance().getReference().child("users").child(u).child("password");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int pos = dataSnapshot.getValue(Integer.class);
                        user.child(u).setValue(new User(username.getText().toString(),password.getText().toString(),pos));
                        Toast.makeText(ProfileActivity.this, "Updated Profile", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
