package org.d3ifcool.cubeacon;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.d3ifcool.cubeacon.activities.DetailRoomActivity;
import org.d3ifcool.cubeacon.activities.StepsActivity;
import org.d3ifcool.cubeacon.fragments.BottomSheetStep;
import org.d3ifcool.cubeacon.models.Beacon;
import org.d3ifcool.cubeacon.models.Room;
import org.d3ifcool.cubeacon.utils.Constants;
import org.d3ifcool.cubeacon.utils.Preferences;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class NavigateActivity extends AppCompatActivity  implements SensorEventListener {

    private ImageView lak, dapur, kantin, mp, laboran, lobby, dosenLb, aslab, lift, toilet, gate, exit, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, user01, user02, user03, user04;
    private ImageView edge01, edge02, edge03, edge04, edge05, edge06, edge07, edge08, edge09, edge10, edge11, edge12, edge13, edge14,
            edge15, edge16, edge17, edge18, edge19, edge20, edge21, edge22, edge23, edge24, edge25, edge26, edge27, edge28, edge29, edge30;

    private ImageView walk, backArrow, direction, btnStart, btnStep, btnStop, signNavigate, btnFinish;
    Button infoRoom;
    PhotoView photoView;

    private int currentPos;
    private TextToSpeech textToSpeech;
    TextView name, floor, jarakTxt, signNav;
    EditText start, end;

    private final String BLUEBERRY = "blueberry";
    private final String COCONUT = "coconut";
    private final String ICE = "ice";
    private final String MINT = "mint";

    // beacon data
    CardView roomInfo, signCd;
    ConstraintLayout signBox;

    // Rute
    ArrayList<String> rute;
    ArrayList<String> arah;
    ArrayList<String> edge;
    ArrayList<String> sign;
    ArrayList<Double> cost;

    // Beacon
    Beacon beacon01, beacon2, beacon3, beacon04, beacon05, beacon06, beacon07, beacon08;

    int userPos;

    String beacons;
    boolean startPressed;

    // Compass
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currentAzimuth = 0f;
    private SensorManager sensorManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startPressed = false;
        checkRef.setValue(false);
//        navRef.setValue(0);
        finish();
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("userPosition");
    DatabaseReference myRef;
    DatabaseReference checkRef = database.getReference("navigation");
    DatabaseReference navRef = database.getReference("nav");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        beacons = "";
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        String users = Preferences.read(getApplicationContext(),Constants.USERNAME,"u");
        myRef  = database.getReference().child("users").child(users).child("userPosition");

        startPressed = false;

        roomInfo = findViewById(R.id.room_info);
        photoView = findViewById(R.id.photo_view);
        name = findViewById(R.id.tv_title_room);
        floor = findViewById(R.id.tv_desc_room);
        infoRoom = findViewById(R.id.btn_info_room);
        direction = findViewById(R.id.btn_direction_nav);
        backArrow = findViewById(R.id.iv_arrow_direction);
        jarakTxt = findViewById(R.id.tv_cost_room);
        walk = findViewById(R.id.imageView3);
        start = findViewById(R.id.et_user_pos);
        end = findViewById(R.id.et_end_pos);
        signCd = findViewById(R.id.cd_sign);
        btnStart = findViewById(R.id.btn_start_nav);
        btnStep = findViewById(R.id.btn_step);
        btnStop = findViewById(R.id.btn_stop_nav);
        signNav = findViewById(R.id.tv_sign_nav);
        signBox = findViewById(R.id.sign_box);
        btnFinish = findViewById(R.id.btn_finish_nav);
        signNavigate = findViewById(R.id.iv_sign_nav);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/mdi_directions_walk.png?alt=media&token=dee5da4b-a623-47ab-a7cd-dbf2c5a50c85").into(walk);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/blue_back.png?alt=media&token=c6f28a6f-660a-45d4-a05c-6b6b78fbf7de").into(backArrow);

        rute = new ArrayList<>();
        arah = new ArrayList<>();
        edge = new ArrayList<>();
        sign = new ArrayList<>();
        cost = new ArrayList<>();

        initPinRoom();
        gonePinRoom();
        goneEdge();

        Room room = getIntent().getParcelableExtra("room");
        userPos = getIntent().getIntExtra("posisi user",100);

        String startPoin = "";
        if (userPos==1){
            startPoin = "beacon01";
        }else if (userPos==2){
            startPoin = "beacon02";
        }else if (userPos==3){
            startPoin = "beacon03";
        }else if (userPos==4){
            startPoin = "beacon04";
        }
        start.setText(startPoin);
        end.setText(room.getName());

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(Integer.class);
                showUser(value);
                if (value==1){
                    beacons = "beacon01";
                }else if (value==2){
                    beacons = "beacon02";
                }else if (value==3){
                    beacons = "beacon03";
                }else if (value==4){
                    beacons = "beacon04";
                }else if (value==0){
                    beacons = "lurus";
                    user01.clearAnimation();
                    user02.clearAnimation();
                    user03.clearAnimation();
                    user04.clearAnimation();
                }
                checkRoute();
                if (startPressed){
                    updateCost();
                }
//                Toast.makeText(NavigateActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        initRute(startPoin,room.getName());

        if (room != null){
            name.setText(room.getName());
            floor.setText(room.getFloor());
            showUser(userPos);
            showPin(room.getName());
        }

        infoRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(NavigateActivity.this, DetailRoomActivity.class);
                detailIntent.putExtra("detail room",room);
                startActivity(detailIntent);
            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Periksa array rute
                for (int i = 0; i < rute.size(); i++) {
                    // Jika anggota rute sama dengan beacon01
                    if (rute.get(i).matches(beacon01.getInformation().getName())){
                        // Periksa object beacon01
                        for (int j = 0; j < beacon01.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon01 sama dengan rute+1
                            if (beacon01.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon01.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon01.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon01.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                    // Jika anggota rute sama dengan beacon02
                    else if (rute.get(i).matches(beacon2.getInformation().getName())){
                        // Periksa object beacon02
                        for (int j = 0; j < beacon2.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon02 sama dengan rute+1
                            if (beacon2.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon2.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon2.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon2.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                    // Jika anggota rute sama dengan beacon03
                    else if (rute.get(i).matches(beacon3.getInformation().getName())){
                        // Periksa object beacon03
                        for (int j = 0; j < beacon3.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon03 sama dengan rute+1
                            if (beacon3.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon3.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon3.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon3.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                    // Jika anggota rute sama dengan beacon04
                    if (rute.get(i).matches(beacon04.getInformation().getName())){
                        // Periksa object beacon04
                        for (int j = 0; j < beacon04.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon04 sama dengan rute+1
                            if (beacon04.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon04.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon04.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon04.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                    // Jika anggota rute sama dengan beacon05
                    else if (rute.get(i).matches(beacon05.getInformation().getName())){
                        // Periksa object beacon05
                        for (int j = 0; j < beacon05.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon05 sama dengan rute+1
                            if (beacon05.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon05.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon05.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon05.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                    // Jika anggota rute sama dengan beacon06
                    else if (rute.get(i).matches(beacon06.getInformation().getName())){
                        // Periksa object beacon03
                        for (int j = 0; j < beacon06.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon06 sama dengan rute+1
                            if (beacon06.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon06.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon06.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon06.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                    // Jika anggota rute sama dengan beacon07
                    else if (rute.get(i).matches(beacon07.getInformation().getName())){
                        // Periksa object beacon03
                        for (int j = 0; j < beacon07.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon07 sama dengan rute+1
                            if (beacon07.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon07.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon07.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon07.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                    // Jika anggota rute sama dengan beacon08
                    else if (rute.get(i).matches(beacon08.getInformation().getName())){
                        // Periksa object beacon03
                        for (int j = 0; j < beacon08.getAlgorithm().get(0).getNeightbor().size(); j++) {
                            // Jika tetangga beacon06 sama dengan rute+1
                            if (beacon08.getAlgorithm().get(0).getNeightbor().get(j).matches(rute.get(i+1))){
                                // Masukan edge & sign sesuai index ke array masing-masing
                                edge.add(beacon08.getAlgorithm().get(0).getEdge().get(j));
                                sign.add(beacon08.getAlgorithm().get(0).getSign().get(j));
                                cost.add(beacon08.getAlgorithm().get(0).getCost().get(j));
                            }
                        }
                    }
                }

                showEdge();
                initCost();

                walk.setVisibility(View.VISIBLE);
                jarakTxt.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                direction.setVisibility(View.INVISIBLE);
                infoRoom.setVisibility(View.INVISIBLE);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigateActivity.this, StepsActivity.class);
                intent.putExtra("steps",arah);
                startActivity(intent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart.setVisibility(View.GONE);
                btnStep.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.VISIBLE);
                signCd.setVisibility(View.VISIBLE);
                startPressed = true;
//                checkRef.setValue(true);
//                signNav.setText(arah.get(0));
                checkRoute();
                updateCost();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPressed = false;
                onBackPressed();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPressed = false;
                onBackPressed();
            }
        });

        speechUhuy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void checkRoute(){
        for (int i = 0; i < rute.size(); i++) {
                if (beacons.equalsIgnoreCase(rute.get(i))){
                    signNav.setText(arah.get(i));
                    updateSignIcon(signNav.getText().toString());
                    if (startPressed){
                        int speech = textToSpeech.speak(arah.get(i),TextToSpeech.QUEUE_FLUSH,null);
                    }
                    signBox.setBackgroundColor(getResources().getColor(R.color.deep_blue));
                }
                else if (beacons.equalsIgnoreCase("beacon04")){
                    signNav.setText(arah.get(arah.size()-1));
                    if (startPressed){
                        int speech = textToSpeech.speak(arah.get(arah.size()-1),TextToSpeech.QUEUE_FLUSH,null);
                    }
                    signBox.setBackgroundColor(getResources().getColor(R.color.green));
                    btnStop.setVisibility(View.GONE);
                    btnStep.setVisibility(View.GONE);
                    btnFinish.setVisibility(View.VISIBLE);
//                    BottomSheetStep bottomSheetStep = new BottomSheetStep();
//                    bottomSheetStep.show(getSupportFragmentManager(),"bottom sheet step");
                    updateSignIcon(signNav.getText().toString());
                }else if (beacons.equalsIgnoreCase("lurus")){
                    signNav.setText("Move Forward");
                    if (startPressed){
                        int speech = textToSpeech.speak("Move Forward",TextToSpeech.QUEUE_FLUSH,null);
                    }
                    signBox.setBackgroundColor(getResources().getColor(R.color.deep_blue));
                    updateSignIcon(signNav.getText().toString());
                }
            }
    }

    private void updateSignIcon(String sign){
        if (sign.equalsIgnoreCase("Arrive at destination")){
            signNavigate.setImageResource(R.drawable.strip);
        }else if (sign.equalsIgnoreCase("Move Forward")){
            signNavigate.setImageResource(R.drawable.up_arrow);
        }else if (sign.equalsIgnoreCase("Turn Right")){
            signNavigate.setImageResource(R.drawable.up_right_arrow);
        }else if (sign.equalsIgnoreCase("Turn Left")){
            signNavigate.setImageResource(R.drawable.up_left_arrow);
        }else if (sign.equalsIgnoreCase("Slight right")){
            signNavigate.setImageResource(R.drawable.slight_right);
        }else if (sign.equalsIgnoreCase("Slight left")){
            signNavigate.setImageResource(R.drawable.slight_left);
        }else if (sign.equalsIgnoreCase("on the right")){
            signNavigate.setImageResource(R.drawable.right_arrow);
        }
        else if (sign.equalsIgnoreCase("on the left")){
            signNavigate.setImageResource(R.drawable.left_arrow);
        }
    }

    private void showUser(int pos){
        switch (pos){
            case 1:
                user01.setVisibility(View.GONE);
                user02.setVisibility(View.GONE);
                user03.setVisibility(View.GONE);
                user04.setVisibility(View.GONE);
                user01.setVisibility(View.VISIBLE);
                break;
            case 2:
                user01.setVisibility(View.GONE);
                user02.setVisibility(View.GONE);
                user03.setVisibility(View.GONE);
                user04.setVisibility(View.GONE);
                user02.setVisibility(View.VISIBLE);
                break;
            case 3:
                user01.setVisibility(View.GONE);
                user02.setVisibility(View.GONE);
                user03.setVisibility(View.GONE);
                user04.setVisibility(View.GONE);
                user03.setVisibility(View.VISIBLE);
                break;
            case 4:
                user01.setVisibility(View.GONE);
                user02.setVisibility(View.GONE);
                user03.setVisibility(View.GONE);
                user04.setVisibility(View.GONE);
                user04.setVisibility(View.VISIBLE);
                break;

//            default:
//                Toast.makeText(this, "Outside Beacon", Toast.LENGTH_SHORT).show();
//                break;
        }
    }

    private void updateCost(){
        for (int i = 0; i < rute.size(); i++) {
            if (beacons.equalsIgnoreCase(rute.get(i))){
                DecimalFormat digit = new DecimalFormat("0.00");
                name.setText(digit.format(cost.get(i))+" m");
            }else if (beacons.equalsIgnoreCase("beacon04")){
                name.setText("0.0 m");
            }
        }
    }

    private void outBeacon(){
        // TODO :  keluar daribeacon hilangin
//        user01.setVisibility(View.GONE);
//        user02.setVisibility(View.GONE);
//        user03.setVisibility(View.GONE);
//        user04.setVisibility(View.GONE);
        userPos = 0;
    }

    private void initCost() {
        DecimalFormat digit = new DecimalFormat("0.00");
        double jarak = 0;
        if (cost.isEmpty()){
            jarakTxt.setText(String.valueOf(digit.format(jarak))+" m");
        }else {
            for (int i = 0; i < cost.size(); i++) {
                jarak += cost.get(i);
            }
            jarakTxt.setText(String.valueOf(digit.format(jarak))+" m");
        }
    }

    private void showEdge(){
        for (String edge : edge) {
            switch (edge){
                case "edge01":
                    edge01.setVisibility(View.VISIBLE);
                    break;
                case "edge02":
                    edge02.setVisibility(View.VISIBLE);
                    break;
                case "edge03":
                    edge03.setVisibility(View.VISIBLE);
                    break;
                case "edge04":
                    edge04.setVisibility(View.VISIBLE);
                    break;
                case "edge05":
                    edge05.setVisibility(View.VISIBLE);
                    break;
                case "edge06":
                    edge06.setVisibility(View.VISIBLE);
                    break;
                case "edge07":
                    edge07.setVisibility(View.VISIBLE);
                    break;
                case "edge08":
                    edge08.setVisibility(View.VISIBLE);
                    break;
                case "edge09":
                    edge09.setVisibility(View.VISIBLE);
                    break;
                case "edge10":
                    edge10.setVisibility(View.VISIBLE);
                    break;
                case "edge11":
                    edge11.setVisibility(View.VISIBLE);
                    break;
                case "edge12":
                    edge12.setVisibility(View.VISIBLE);
                    break;
                case "edge13":
                    edge13.setVisibility(View.VISIBLE);
                    break;
                case "edge14":
                    edge14.setVisibility(View.VISIBLE);
                    break;
                case "edge15":
                    edge15.setVisibility(View.VISIBLE);
                    break;
                case "edge16":
                    edge16.setVisibility(View.VISIBLE);
                    break;
                case "edge17":
                    edge17.setVisibility(View.VISIBLE);
                    break;
                case "edge18":
                    edge18.setVisibility(View.VISIBLE);
                    break;
                case "edge19":
                    edge19.setVisibility(View.VISIBLE);
                    break;
                case "edge20":
                    edge20.setVisibility(View.VISIBLE);
                    break;
                case "edge21":
                    edge21.setVisibility(View.VISIBLE);
                    break;
                case "edge22":
                    edge22.setVisibility(View.VISIBLE);
                    break;
                case "edge23":
                    edge23.setVisibility(View.VISIBLE);
                    break;
                case "edge24":
                    edge24.setVisibility(View.VISIBLE);
                    break;
                case "edge25":
                    edge25.setVisibility(View.VISIBLE);
                    break;
                case "edge26":
                    edge26.setVisibility(View.VISIBLE);
                    break;
                case "edge27":
                    edge27.setVisibility(View.VISIBLE);
                    break;
                case "edge28":
                    edge28.setVisibility(View.VISIBLE);
                    break;
                case "edge29":
                    edge29.setVisibility(View.VISIBLE);
                    break;
                case "edge30":
                    edge30.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void initBeacon(){
        // Beacon 01
        String jsonFileStringBeacon01 = Utils.getJsonFromAssets(getApplicationContext(), "beacon01.json");
        Log.i("data", jsonFileStringBeacon01);

        Gson gsonBeaconOne = new Gson();
        beacon01 = gsonBeaconOne.fromJson(jsonFileStringBeacon01, Beacon.class);

        // Beacon 02
        String jsonFileStringBeacon02 = Utils.getJsonFromAssets(getApplicationContext(), "blueberry.json");
        Log.i("data", jsonFileStringBeacon02);

        Gson gsonBlueberry = new Gson();
        beacon2 = gsonBlueberry.fromJson(jsonFileStringBeacon02, Beacon.class);

        // Beacon 03
        String jsonFileStringBeacon03 = Utils.getJsonFromAssets(getApplicationContext(), "coconut.json");
        Log.i("data", jsonFileStringBeacon03);

        Gson gsonCoconut = new Gson();
        beacon3 = gsonCoconut.fromJson(jsonFileStringBeacon03, Beacon.class);

        // Beacon 04
        String jsonFileStringBeacon04 = Utils.getJsonFromAssets(getApplicationContext(), "beacon04.json");
        Log.i("data", jsonFileStringBeacon04);

        Gson gsonBeaconFour = new Gson();
        beacon04 = gsonBeaconFour.fromJson(jsonFileStringBeacon04, Beacon.class);

        // Beacon 05
        String jsonFileStringBeacon05 = Utils.getJsonFromAssets(getApplicationContext(), "beacon05.json");
        Log.i("data", jsonFileStringBeacon05);

        Gson gsonBeaconFive = new Gson();
        beacon05 = gsonBeaconFive.fromJson(jsonFileStringBeacon05, Beacon.class);

        // Beacon 06
        String jsonFileStringBeacon06 = Utils.getJsonFromAssets(getApplicationContext(), "beacon06.json");
        Log.i("data", jsonFileStringBeacon06);

        Gson gsonBeaconSix = new Gson();
        beacon06 = gsonBeaconSix.fromJson(jsonFileStringBeacon06, Beacon.class);

        // Beacon 07
        String jsonFileStringBeacon07 = Utils.getJsonFromAssets(getApplicationContext(), "beacon07.json");
        Log.i("data", jsonFileStringBeacon07);

        Gson gsonBeaconSeven = new Gson();
        beacon07 = gsonBeaconSeven.fromJson(jsonFileStringBeacon07, Beacon.class);

        // Beacon 08
        String jsonFileStringBeacon08 = Utils.getJsonFromAssets(getApplicationContext(), "beacon08.json");
        Log.i("data", jsonFileStringBeacon08);

        Gson gsonBeaconEight = new Gson();
        beacon08 = gsonBeaconEight.fromJson(jsonFileStringBeacon08, Beacon.class);
    }

    private void speechUhuy() {
        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS){
                            int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                        }
                    }
                });
    }

    private void showPin(String name){
        switch (name.toLowerCase()){
            case "admin lab":
                aslab.setVisibility(View.VISIBLE);
                break;
            case "kitchen":
                dapur.setVisibility(View.VISIBLE);
                break;
            case "laboran":
                laboran.setVisibility(View.VISIBLE);
                break;
            case "lak":
                lak.setVisibility(View.VISIBLE);
                break;
            case "mp mart":
                mp.setVisibility(View.VISIBLE);
                break;
            case "toilet":
                toilet.setVisibility(View.VISIBLE);
                break;
            case "g1":
                g1.setVisibility(View.VISIBLE);
                break;
            case "g2":
                g2.setVisibility(View.VISIBLE);
                break;
            case "g3":
                g3.setVisibility(View.VISIBLE);
                break;
            case "g4":
                g4.setVisibility(View.VISIBLE);
                break;
            case "g5":
                g5.setVisibility(View.VISIBLE);
                break;
            case "g6":
                g6.setVisibility(View.VISIBLE);
                break;
            case "g7":
                g7.setVisibility(View.VISIBLE);
                break;
            case "g8":
                g8.setVisibility(View.VISIBLE);
                break;
            case "g9":
                g9.setVisibility(View.VISIBLE);
                break;
            case "g10":
                g10.setVisibility(View.VISIBLE);
                break;
            case "g11":
                g11.setVisibility(View.VISIBLE);
                break;
            case "g12":
                g12.setVisibility(View.VISIBLE);
                break;
            case "kantin":
                kantin.setVisibility(View.VISIBLE);
                break;
            case "lobby":
                lobby.setVisibility(View.VISIBLE);
                break;
            case "lift":
                lift.setVisibility(View.VISIBLE);
                break;
            case "dosen lb":
                dosenLb.setVisibility(View.VISIBLE);
                break;
            case "gate":
                gate.setVisibility(View.VISIBLE);
                break;
            case "exit":
                exit.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initPinRoom(){
        user01 = findViewById(R.id.user01);
        user02 = findViewById(R.id.user02);
        user03 = findViewById(R.id.user03);
        user04 = findViewById(R.id.user04);
        dapur = findViewById(R.id.node_dapur);
        kantin = findViewById(R.id.node_kantin);
        mp = findViewById(R.id.node_mp_mart);
        dosenLb = findViewById(R.id.node_dosen_lb);
        aslab = findViewById(R.id.node_aslab);
        lift = findViewById(R.id.node_lift);
        toilet = findViewById(R.id.node_toilet);
        gate = findViewById(R.id.node_gate);
        exit = findViewById(R.id.node_exit_emergency);
        laboran = findViewById(R.id.node_laboran);
        lobby = findViewById(R.id.node_lobby);
        lak = findViewById(R.id.node_lak);
        g1 = findViewById(R.id.node_g1);
        g2 = findViewById(R.id.node_g2);
        g3 = findViewById(R.id.node_g3);
        g4 = findViewById(R.id.node_g4);
        g5 = findViewById(R.id.node_g5);
        g6 = findViewById(R.id.node_g6);
        g7 = findViewById(R.id.node_g7);
        g8 = findViewById(R.id.node_g8);
        g9 = findViewById(R.id.node_g9);
        g10 = findViewById(R.id.node_g10);
        g11 = findViewById(R.id.node_g11);
        g12 = findViewById(R.id.node_g12);

        // Edge
        edge01 = findViewById(R.id.edge01);
        edge02 = findViewById(R.id.edge02);
        edge03 = findViewById(R.id.edge03);
        edge04 = findViewById(R.id.edge04);
        edge05 = findViewById(R.id.edge05);
        edge06 = findViewById(R.id.edge06);
        edge07 = findViewById(R.id.edge07);
        edge08 = findViewById(R.id.edge08);
        edge09 = findViewById(R.id.edge09);
        edge10 = findViewById(R.id.edge10);
        edge11 = findViewById(R.id.edge11);
        edge12 = findViewById(R.id.edge12);
        edge13 = findViewById(R.id.edge13);
        edge14 = findViewById(R.id.edge14);
        edge15 = findViewById(R.id.edge15);
        edge16 = findViewById(R.id.edge16);
        edge17 = findViewById(R.id.edge17);
        edge18 = findViewById(R.id.edge18);
        edge19 = findViewById(R.id.edge19);
        edge20 = findViewById(R.id.edge20);
        edge21 = findViewById(R.id.edge21);
        edge22 = findViewById(R.id.edge22);
        edge23 = findViewById(R.id.edge23);
        edge24 = findViewById(R.id.edge24);
        edge25 = findViewById(R.id.edge25);
        edge26 = findViewById(R.id.edge26);
        edge27 = findViewById(R.id.edge27);
        edge28 = findViewById(R.id.edge28);
        edge29 = findViewById(R.id.edge29);
        edge30 = findViewById(R.id.edge30);
    }

    private void gonePinRoom(){
        user01.setVisibility(View.INVISIBLE);
        user02.setVisibility(View.INVISIBLE);
        user03.setVisibility(View.INVISIBLE);
        user04.setVisibility(View.INVISIBLE);
        dapur.setVisibility(View.INVISIBLE);
        kantin.setVisibility(View.INVISIBLE);
        mp.setVisibility(View.INVISIBLE);
        dosenLb.setVisibility(View.INVISIBLE);
        aslab.setVisibility(View.INVISIBLE);
        lift.setVisibility(View.INVISIBLE);
        toilet.setVisibility(View.INVISIBLE);
        gate.setVisibility(View.INVISIBLE);
        exit.setVisibility(View.INVISIBLE);
        laboran.setVisibility(View.INVISIBLE);
        lak.setVisibility(View.INVISIBLE);
        lobby.setVisibility(View.INVISIBLE);
        g1.setVisibility(View.INVISIBLE);
        g2.setVisibility(View.INVISIBLE);
        g3.setVisibility(View.INVISIBLE);
        g4.setVisibility(View.INVISIBLE);
        g5.setVisibility(View.INVISIBLE);
        g6.setVisibility(View.INVISIBLE);
        g7.setVisibility(View.INVISIBLE);
        g8.setVisibility(View.INVISIBLE);
        g9.setVisibility(View.INVISIBLE);
        g10.setVisibility(View.INVISIBLE);
        g11.setVisibility(View.INVISIBLE);
        g12.setVisibility(View.INVISIBLE);

        signCd.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);
        btnStep.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);

        btnStart.setVisibility(View.GONE);
        walk.setVisibility(View.GONE);
        jarakTxt.setVisibility(View.GONE);
    }

    private void goneEdge(){
        edge01.setVisibility(View.INVISIBLE);
        edge02.setVisibility(View.INVISIBLE);
        edge03.setVisibility(View.INVISIBLE);
        edge04.setVisibility(View.INVISIBLE);
        edge05.setVisibility(View.INVISIBLE);
        edge06.setVisibility(View.INVISIBLE);
        edge07.setVisibility(View.INVISIBLE);
        edge08.setVisibility(View.INVISIBLE);
        edge09.setVisibility(View.INVISIBLE);
        edge10.setVisibility(View.INVISIBLE);
        edge11.setVisibility(View.INVISIBLE);
        edge12.setVisibility(View.INVISIBLE);
        edge13.setVisibility(View.INVISIBLE);
        edge14.setVisibility(View.INVISIBLE);
        edge15.setVisibility(View.INVISIBLE);
        edge16.setVisibility(View.INVISIBLE);
        edge17.setVisibility(View.INVISIBLE);
        edge18.setVisibility(View.INVISIBLE);
        edge19.setVisibility(View.INVISIBLE);
        edge20.setVisibility(View.INVISIBLE);
        edge21.setVisibility(View.INVISIBLE);
        edge22.setVisibility(View.INVISIBLE);
        edge23.setVisibility(View.INVISIBLE);
        edge24.setVisibility(View.INVISIBLE);
        edge25.setVisibility(View.INVISIBLE);
        edge26.setVisibility(View.INVISIBLE);
        edge27.setVisibility(View.INVISIBLE);
        edge28.setVisibility(View.INVISIBLE);
        edge29.setVisibility(View.INVISIBLE);
        edge30.setVisibility(View.INVISIBLE);
    }

    private void initRute(String start, String end){
        Route routes = new Route(NavigateActivity.this);
        ArrayList<ArrayList<String>> navigasi = new ArrayList<>();

//        navigasi = routes.findRoute(start,end);
        navigasi = routes.findWay(start,end);

        rute.addAll(navigasi.get(0));
        arah.addAll(navigasi.get(1));
        String lowerGoal = rute.get(rute.size()-1).toLowerCase();
        rute.set(rute.size()-1,lowerGoal);

        String g = "";
        for (int i = 0; i < rute.size() ; i++) {
            g += ","+rute.get(i);
        }
        Toast.makeText(NavigateActivity.this, g, Toast.LENGTH_SHORT).show();


        initBeacon();
//        if (end.equalsIgnoreCase("g9")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("g9");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g10")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("g10");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("laboran")){
//            rute.add("beacon02");
//            rute.add("laboran");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("mp mart")){
//            rute.add("beacon02");
//            rute.add("mpmart");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("gate")){
//            rute.add("beacon02");
//            rute.add("gate");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g11")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("g11");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("lobby")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("lobby");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("kitchen")){
//            rute.add("beacon02");
//            rute.add("beacon01");
//            rute.add("dapur");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g5")){
//            rute.add("beacon02");
//            rute.add("beacon01");
//            rute.add("g5");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g1")){
//            rute.add("beacon02");
//            rute.add("beacon01");
//            rute.add("g1");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g6")){
//            rute.add("beacon02");
//            rute.add("beacon01");
//            rute.add("g6");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g7")){
//            rute.add("beacon02");
//            rute.add("beacon01");
//            rute.add("g7");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("kantin")){
//            rute.add("beacon02");
//            rute.add("beacon01");
//            rute.add("kantin");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g3")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon06");
//            rute.add("g3");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g4")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon06");
//            rute.add("g4");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g8")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon06");
//            rute.add("g8");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("g2")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon06");
//            rute.add("g2");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("lift")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon07");
//            rute.add("lift");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("admin lab")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon07");
//            rute.add("admin");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("toilet")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon07");
//            rute.add("beacon08");
//            rute.add("toilet");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("exit")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon07");
//            rute.add("beacon08");
//            rute.add("exit");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("lak")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("lak");
//            initBeacon();
//        }else if (end.equalsIgnoreCase("dosen lb")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon07");
//            rute.add("dosen lb");
//            initBeacon();
//        }
//        else if (end.equalsIgnoreCase("g12")){
//            rute.add("beacon02");
//            rute.add("beacon03");
//            rute.add("beacon04");
//            rute.add("beacon05");
//            rute.add("beacon07");
//            rute.add("g12");
//            initBeacon();
//        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float alpha = 0.97f;
        synchronized (this){
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mGravity[0] = alpha*mGravity[0]+(1-alpha)*sensorEvent.values[0];
                mGravity[1] = alpha*mGravity[1]+(1-alpha)*sensorEvent.values[1];
                mGravity[2] = alpha*mGravity[2]+(1-alpha)*sensorEvent.values[2];
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                mGeomagnetic[0] = alpha*mGeomagnetic[0]+(1-alpha)*sensorEvent.values[0];
                mGeomagnetic[1] = alpha*mGeomagnetic[1]+(1-alpha)*sensorEvent.values[1];
                mGeomagnetic[2] = alpha*mGeomagnetic[2]+(1-alpha)*sensorEvent.values[2];
            }
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R,I,mGravity,mGeomagnetic);
            if (success){
                float orientation[] = new float[3];
                SensorManager.getOrientation(R,orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth+360)%360;

                Animation anim = new RotateAnimation(-currentAzimuth,-azimuth,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                currentAzimuth = azimuth;

                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                if (beacons.equalsIgnoreCase("beacon01")){
                    user01.startAnimation(anim);
                }else if (beacons.equalsIgnoreCase("beacon02")){
                    user02.startAnimation(anim);
                }else if (beacons.equalsIgnoreCase("beacon03")){
                    user03.startAnimation(anim);
                }else if (beacons.equalsIgnoreCase("beacon04")){
                    user04.startAnimation(anim);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
