package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import java.util.Locale;

public class ZoomActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button up, down, nav;
    private ZoomControls zoomControls;
    private TextView explore;
    ViewGroup viewGroup;

    TextToSpeech textToSpeech;

    int xDelta, yDelta;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        imageView = findViewById(R.id.imageView);
        zoomControls = findViewById(R.id.zoom);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        nav = findViewById(R.id.navigate);
        explore = findViewById(R.id.textView);
        viewGroup = findViewById(R.id.zoom_layout);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZoomActivity.this, NavigateActivity.class);
                startActivity(intent);
            }
        });

        // TODO : zoom & drag map
        zoomControls.hide();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                zoomControls.show();
                return true;
            }
        });

        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float x = imageView.getScaleX();
                float y = imageView.getScaleY();
                explore.setVisibility(View.INVISIBLE);

                if (x == 2 && y ==2){
                    imageView.setScaleX(x);
                    imageView.setScaleY(y);
                }else {
                    imageView.setScaleX((float) (x+1));
                    imageView.setScaleY((float) (y+1));
                    zoomControls.hide();

                    imageView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            final int xo = (int) motionEvent.getRawX();
                            final int yo = (int) motionEvent.getRawY();

                            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                                case MotionEvent.ACTION_DOWN :

                                    ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                    xDelta = xo - param.leftMargin;
                                    yDelta = yo - param.topMargin;
                                    break;

                                case MotionEvent.ACTION_MOVE :

                                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                    layoutParams.leftMargin = xo - xDelta;
                                    layoutParams.topMargin = yo - yDelta;
                                    layoutParams.rightMargin = 500;
                                    layoutParams.bottomMargin = 250;
                                    view.setLayoutParams(layoutParams);
                                    break;

                                case MotionEvent.ACTION_UP :
                                    break;
                            }
                            view.invalidate();
                            zoomControls.show();
                            return true;
                        }
                    });
                }
            }
        });

        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                float x = imageView.getScaleX();
//                float y = imageView.getScaleY();
//                if (x == 1 && y == 1){
//                    imageView.setScaleX(x);
//                    imageView.setScaleY(y);
//                }else {
//                    imageView.setScaleX(x-1);
//                    imageView.setScaleY(y-1);
//                    zoomControls.hide();
//                }
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
            }
        });

        // TODO : text to speech
        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS){
                            int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                        }
                    }
                });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speech = textToSpeech.speak("go forward",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speech = textToSpeech.speak("down",TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }
}
