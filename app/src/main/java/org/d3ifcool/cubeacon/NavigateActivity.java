package org.d3ifcool.cubeacon;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class NavigateActivity extends AppCompatActivity  {

    private ImageView userIcon, pinLab, pinDapur;
    Button up, btnLab, btnDapur, btnBeaconOne, closeCard;
    PhotoView photoView;
    Animation aU, aL;

    private int currentPos;
    private TextToSpeech textToSpeech;

    // estimote
    private NotificationManagaer notificationManagaer;
    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("febbydahlan034-gmail-com-s-6wz", "93eb2e64e84caf1d30079ad3c7b8b7e8");

    // beacon data
    CardView cardView, roomInfo;
    TextView bName, bNode, bTag, n1, n2, n3, n4, n5, c1, c2, c3, c4, c5, jalur, judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        cardView = findViewById(R.id.card_view);
        roomInfo = findViewById(R.id.room_info);
        roomInfo.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        bName = findViewById(R.id.b_name);
        bNode = findViewById(R.id.b_node);
        bTag = findViewById(R.id.b_tag);
        n1 = findViewById(R.id.n_1);
        n2 = findViewById(R.id.n_2);
        n3 = findViewById(R.id.n_3);
        n4 = findViewById(R.id.n_4);
        n5 = findViewById(R.id.n_5);
        c1 = findViewById(R.id.c_1);
        c2 = findViewById(R.id.c_2);
        c3 = findViewById(R.id.c_3);
        c4 = findViewById(R.id.c_4);
        c5 = findViewById(R.id.c_5);
        jalur = findViewById(R.id.tv_jalur);
        closeCard = findViewById(R.id.btn_cls_card);
        judul = findViewById(R.id.tv_judul_ruangan);

        userIcon = findViewById(R.id.node_user);
        userIcon.setVisibility(View.INVISIBLE);
        pinDapur = findViewById(R.id.pin_dapur);
        pinDapur.setVisibility(View.GONE);
        pinLab = findViewById(R.id.pin_lab);
        pinLab.setVisibility(View.GONE);
        photoView = findViewById(R.id.photo_view);

        up = findViewById(R.id.btn_up);
        btnLab = findViewById(R.id.btn_csas);
        btnDapur = findViewById(R.id.btn_dapur);
        btnBeaconOne = findViewById(R.id.btn_beacon_one);

        aU = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        aL = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_left);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speech = textToSpeech.speak("Clear",TextToSpeech.QUEUE_FLUSH,null);
                pinLab.setVisibility(View.GONE);
                pinDapur.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                roomInfo.setVisibility(View.GONE);
                jalur.setText("");
                // Todo : move image
//        // Get the TextView current LayoutParams
//        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) userIcon.getLayoutParams();
//
//        // Set TextView layout margin 25 pixels to all side
//        // Left Top Right Bottom Margin
//        lp.setMargins(0,25,0,0);
//
//        // Apply the updated layout parameters to TextView
//        userIcon.setLayoutParams(lp);
            }
        });

        btnLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userIcon.startAnimation(aU);
                pinLab.setVisibility(View.VISIBLE);
                pinDapur.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                jalur.setText("User ke Lab CSAS : \n" +
                        "beacon01 -> beacon03 -> beacon04 -> beacon05 -> lab csas");
                int speech = textToSpeech.speak("Go to Laboratorium Chevalier SAS",TextToSpeech.QUEUE_FLUSH,null);
                roomInfo.setVisibility(View.VISIBLE);
                judul.setText("Lab CSAS");
            }
        });

        btnDapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userIcon.startAnimation(aL);
                pinDapur.setVisibility(View.VISIBLE);
                pinLab.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                jalur.setText("User ke Dapur : \n" +
                        "beacon01 -> beacon02 -> dapur");
                int speech = textToSpeech.speak("Go to kitchen",TextToSpeech.QUEUE_FLUSH,null);
                roomInfo.setVisibility(View.VISIBLE);
                judul.setText("Dapur");
            }
        });

        btnBeaconOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson();
                pinLab.setVisibility(View.GONE);
                pinDapur.setVisibility(View.GONE);
                roomInfo.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                jalur.setText("Beacon 01 Information");
                int speech = textToSpeech.speak("display beacon 1 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        closeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jalur.setText("");
                cardView.setVisibility(View.GONE);
            }
        });

        // Estimote
        estimoteBlueberry();

        // text to speech
        speechUhuy();

    }

    public void gson(){
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "beacon.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
//        Type listBeaconType = new TypeToken<List<Beacon>>(){}.getType();

        Beacon beacons1 = gson.fromJson(jsonFileString, Beacon.class);
//        Log.i("data",beacons1.getAlgorithm().get(0).getEdge().get(0));

        bName.setText(beacons1.getInformation().getName());
        bTag.setText(beacons1.getInformation().getTags());
        bNode.setText(beacons1.getInformation().getNode());
        n1.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(0));
        n2.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(1));
        n3.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(2));
        n4.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(3));
        n5.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(4));
        c1.setText(beacons1.getAlgorithm().get(0).getCost().get(0).toString());
        c2.setText(beacons1.getAlgorithm().get(0).getCost().get(1).toString());
        c3.setText(beacons1.getAlgorithm().get(0).getCost().get(2).toString());
        c4.setText(beacons1.getAlgorithm().get(0).getCost().get(3).toString());
        c5.setText(beacons1.getAlgorithm().get(0).getCost().get(4).toString());

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

    private void estimoteBlueberry() {
        notificationManagaer = new NotificationManagaer(this);

        // Create the Proximity Observer
        ProximityObserver proximityObserver =
                new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                        .onError(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withBalancedPowerMode()
                        .build();

        // TODO : Create Proximity Zone

        // Near Zone
        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("node-blueberry")
                .inNearRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        currentPos = 1;
                        notificationManagaer.enterBlueberry();
                        Toast.makeText(NavigateActivity.this, "blue in 1 meter "+currentPos, Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.VISIBLE);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManagaer.exitBlueberry();
                        Toast.makeText(NavigateActivity.this, "blue out Blueberry", Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.INVISIBLE);
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(zone);

        // Inner Zone
        ProximityZone innerZone = new ProximityZoneBuilder()
                .forTag("node-blueberry")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        currentPos = 1;
                        notificationManagaer.enterBlueberry();
                        Toast.makeText(NavigateActivity.this, "blue in 3 meter"+currentPos, Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.VISIBLE);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManagaer.exitBlueberry();
                        Toast.makeText(NavigateActivity.this, "blue out 3 meter", Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.INVISIBLE);
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(innerZone);

        // Outer zone
        ProximityZone outerZone = new ProximityZoneBuilder()
                .forTag("node-blueberry")
                .inFarRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        currentPos = 1;
                        notificationManagaer.enterBlueberry();
                        Toast.makeText(NavigateActivity.this, "blue in 5 meter"+currentPos, Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.VISIBLE);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManagaer.exitBlueberry();
                        Toast.makeText(NavigateActivity.this, "blue out Blueberry", Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.INVISIBLE);
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(outerZone);


        // Request location permissions & Start proximity observation
        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        // onRequirementsFulfilled
                        new Function0<Unit>() {
                            @Override public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
//                                Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
                                return null;
                            }
                        },
                        // onRequirementsMissing
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        // onError
                        new Function1<Throwable, Unit>() {
                            @Override public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });
    }
}
