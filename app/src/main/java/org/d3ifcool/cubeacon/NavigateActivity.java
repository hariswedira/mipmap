package org.d3ifcool.cubeacon;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.github.chrisbanes.photoview.PhotoView;

import org.d3ifcool.cubeacon.activities.DetailRoomActivity;
import org.d3ifcool.cubeacon.models.Room;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class NavigateActivity extends AppCompatActivity  {

    private ImageView lak, dapur, kantin, mp, laboran, lobby, dosenLb, aslab, lift, toilet, gate, exit, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, user02, user03;
    private ImageView edge01, edge02, edge03, edge04, edge05, edge06, edge07;

    private ImageView userIcon, backArrow;
    Button infoRoom, direction;
    PhotoView photoView;

    private int currentPos;
    private TextToSpeech textToSpeech;
    TextView name, floor;

    // estimote
    private NotificationManagaer notificationManagaer;
    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("febbydahlan034-gmail-com-s-6wz", "93eb2e64e84caf1d30079ad3c7b8b7e8");

    // beacon data
    CardView cardView, roomInfo, cdDirection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        roomInfo = findViewById(R.id.room_info);
        photoView = findViewById(R.id.photo_view);
        name = findViewById(R.id.tv_title_room);
        floor = findViewById(R.id.tv_desc_room);
        infoRoom = findViewById(R.id.btn_info_room);
        direction = findViewById(R.id.btn_direction_nav);
        cdDirection = findViewById(R.id.cd_direction);
        backArrow = findViewById(R.id.iv_arrow_direction);

        cdDirection.setVisibility(View.INVISIBLE);

        initPinRoom();
        gonePinRoom();
        goneEdge();

        Room room = getIntent().getParcelableExtra("room");
        int userPos = getIntent().getIntExtra("posisi user",100);

        if (room != null){
            name.setText(room.getName());
            floor.setText(room.getFloor());
            showPin(room.getName());
            showUser(userPos);
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
                Toast.makeText(NavigateActivity.this, "a", Toast.LENGTH_SHORT).show();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        speechUhuy();

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

    private void showUser(int pos){
        switch (pos){
            case 2:
                user02.setVisibility(View.VISIBLE);
                break;
            case 3:
                user03.setVisibility(View.VISIBLE);
                break;
            default:
                Toast.makeText(this, "Outside Beacon", Toast.LENGTH_SHORT).show();
                break;
        }
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
        }
    }

//    public void gson(){
//        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "blueberry.json");
//        Log.i("data", jsonFileString);
//
//        Gson gson = new Gson();
////        Type listBeaconType = new TypeToken<List<Beacon>>(){}.getType();
//
//        Beacon beacons1 = gson.fromJson(jsonFileString, Beacon.class);
////        Log.i("data",beacons1.getAlgorithm().get(0).getEdge().get(0));
//
//        bName.setText(beacons1.getInformation().getName());
//        bTag.setText(beacons1.getInformation().getTags());
//        bNode.setText(beacons1.getInformation().getNode());
//        n1.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(0));
//        n2.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(1));
//        n3.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(2));
//        n4.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(3));
//        n5.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(4));
//        c1.setText(beacons1.getAlgorithm().get(0).getCost().get(0).toString());
//        c2.setText(beacons1.getAlgorithm().get(0).getCost().get(1).toString());
//        c3.setText(beacons1.getAlgorithm().get(0).getCost().get(2).toString());
//        c4.setText(beacons1.getAlgorithm().get(0).getCost().get(3).toString());
//        c5.setText(beacons1.getAlgorithm().get(0).getCost().get(4).toString());
//
//    }

//    private void estimoteBlueberry() {
//        notificationManagaer = new NotificationManagaer(this);
//
//        // Create the Proximity Observer
//        ProximityObserver proximityObserver =
//                new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
//                        .onError(new Function1<Throwable, Unit>() {
//                            @Override
//                            public Unit invoke(Throwable throwable) {
//                                Log.e("app", "proximity observer error: " + throwable);
//                                return null;
//                            }
//                        })
//                        .withBalancedPowerMode()
//                        .build();
//
//        // TODO : Create Proximity Zone
//
//        // Near Zone
//        ProximityZone zone = new ProximityZoneBuilder()
//                .forTag("node-blueberry")
//                .inNearRange()
//                .onEnter(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext context) {
//                        currentPos = 1;
//                        notificationManagaer.enterBlueberry();
//                        Toast.makeText(NavigateActivity.this, "blue in 1 meter "+currentPos, Toast.LENGTH_SHORT).show();
//                        userIcon.setVisibility(View.VISIBLE);
//                        return null;
//                    }
//                })
//                .onExit(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext proximityContext) {
////                        notificationManagaer.exitBlueberry();
//                        Toast.makeText(NavigateActivity.this, "blue out Blueberry", Toast.LENGTH_SHORT).show();
//                        userIcon.setVisibility(View.INVISIBLE);
//                        return null;
//                    }
//                })
//                .build();
//        proximityObserver.startObserving(zone);
//
//        // Inner Zone
//        ProximityZone innerZone = new ProximityZoneBuilder()
//                .forTag("node-blueberry")
//                .inCustomRange(3.0)
//                .onEnter(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext context) {
//                        currentPos = 1;
//                        notificationManagaer.enterBlueberry();
//                        Toast.makeText(NavigateActivity.this, "blue in 3 meter"+currentPos, Toast.LENGTH_SHORT).show();
//                        userIcon.setVisibility(View.VISIBLE);
//                        return null;
//                    }
//                })
//                .onExit(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext proximityContext) {
////                        notificationManagaer.exitBlueberry();
//                        Toast.makeText(NavigateActivity.this, "blue out 3 meter", Toast.LENGTH_SHORT).show();
//                        userIcon.setVisibility(View.INVISIBLE);
//                        return null;
//                    }
//                })
//                .build();
//        proximityObserver.startObserving(innerZone);
//
//        // Outer zone
//        ProximityZone outerZone = new ProximityZoneBuilder()
//                .forTag("node-blueberry")
//                .inFarRange()
//                .onEnter(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext context) {
//                        currentPos = 1;
//                        notificationManagaer.enterBlueberry();
//                        Toast.makeText(NavigateActivity.this, "blue in 5 meter"+currentPos, Toast.LENGTH_SHORT).show();
//                        userIcon.setVisibility(View.VISIBLE);
//                        return null;
//                    }
//                })
//                .onExit(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext proximityContext) {
////                        notificationManagaer.exitBlueberry();
//                        Toast.makeText(NavigateActivity.this, "blue out Blueberry", Toast.LENGTH_SHORT).show();
//                        userIcon.setVisibility(View.INVISIBLE);
//                        return null;
//                    }
//                })
//                .build();
//        proximityObserver.startObserving(outerZone);
//
//
//        // Request location permissions & Start proximity observation
//        RequirementsWizardFactory
//                .createEstimoteRequirementsWizard()
//                .fulfillRequirements(this,
//                        // onRequirementsFulfilled
//                        new Function0<Unit>() {
//                            @Override public Unit invoke() {
//                                Log.d("app", "requirements fulfilled");
////                                Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
//                                return null;
//                            }
//                        },
//                        // onRequirementsMissing
//                        new Function1<List<? extends Requirement>, Unit>() {
//                            @Override public Unit invoke(List<? extends Requirement> requirements) {
//                                Log.e("app", "requirements missing: " + requirements);
//                                return null;
//                            }
//                        },
//                        // onError
//                        new Function1<Throwable, Unit>() {
//                            @Override public Unit invoke(Throwable throwable) {
//                                Log.e("app", "requirements error: " + throwable);
//                                return null;
//                            }
//                        });
//    }

    private void initPinRoom(){
        user02 = findViewById(R.id.user02);
        user03 = findViewById(R.id.user03);
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
    }

    private void gonePinRoom(){
        user02.setVisibility(View.INVISIBLE);
        user03.setVisibility(View.INVISIBLE);
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
    }

    private void goneEdge(){
        edge01.setVisibility(View.INVISIBLE);
        edge02.setVisibility(View.INVISIBLE);
        edge03.setVisibility(View.INVISIBLE);
        edge04.setVisibility(View.INVISIBLE);
        edge05.setVisibility(View.INVISIBLE);
        edge06.setVisibility(View.INVISIBLE);
        edge07.setVisibility(View.INVISIBLE);
    }
}
