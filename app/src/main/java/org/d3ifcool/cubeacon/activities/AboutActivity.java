package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.d3ifcool.cubeacon.R;

public class AboutActivity extends AppCompatActivity {

    ImageView hs, hy, gbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        hs = findViewById(R.id.imageView7);
        hy = findViewById(R.id.imageView8);
        gbs = findViewById(R.id.imageView9);

        String pHs = "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/haris.png?alt=media&token=33b49bcb-c5f5-4c36-bd34-6cfe34fc2984";
        String pHy = "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/hisyam.png?alt=media&token=8eda6cc0-8efd-445e-9b1f-c0b27c98ff5e";
        String pGbs = "https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/gbs.png?alt=media&token=960a2dc2-ee62-4f72-a24e-540a709f4a43";

        Glide.with(this).load(pHs).placeholder(R.drawable.image_placeholder).into(hs);
        Glide.with(this).load(pHy).placeholder(R.drawable.image_placeholder).into(hy);
        Glide.with(this).load(pGbs).placeholder(R.drawable.image_placeholder).into(gbs);
    }
}
