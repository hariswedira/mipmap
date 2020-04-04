package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
import com.google.gson.Gson;

import org.d3ifcool.cubeacon.activities.ListEventActivity;

import java.util.List;
import java.util.Locale;

public class EventActivity extends AppCompatActivity {

    public EstimoteCloudCredentials cloudCredentials =
        new EstimoteCloudCredentials("febbydahlan034-gmail-com-s-6wz", "93eb2e64e84caf1d30079ad3c7b8b7e8");
    private NotificationManagaer notificationManagaer;

    private final String BLUEBERRY = "blueberry";
    private final String COCONUT = "coconut";

    private int userPos;
    private TextView noEvent;
    private ImageView searchMenu, pinUserTwo, pinUserThree, pinBcnTwo, pinBcnThree, pinBcnFour, pinBcnFive, pinBcnSix, pinBcnSeven, pinBcnEight, pinBcnOne;
    TextView bName, bNode, bTag, n1, n2, n3, n4, n5, n6, n7, c1, c2, c3, c4, c5, c6, c7;
    Button closeCard;
    CardView cardView, cdEvent;
    private TextToSpeech textToSpeech;

    public final String EVENT_ID = "event_id";
    private String event_tap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        userPos = 0;
        noEvent = findViewById(R.id.tv_no_event);
        searchMenu = findViewById(R.id.iv_search_room);
        pinUserTwo = findViewById(R.id.pin_user_pos_two);
        pinUserThree = findViewById(R.id.pin_user_pos_three);
        pinBcnTwo = findViewById(R.id.pin_bcn_two);
        pinBcnThree = findViewById(R.id.pin_bcn_three);
        pinBcnOne = findViewById(R.id.pin_bcn_one);
        pinBcnFour = findViewById(R.id.pin_bcn_four);
        pinBcnFive = findViewById(R.id.pin_bcn_five);
        pinBcnSix = findViewById(R.id.pin_bcn_seven);
        pinBcnSeven = findViewById(R.id.pin_bcn_six);
        pinBcnEight = findViewById(R.id.pin_bcn_eight);

        cardView = findViewById(R.id.cd_info);
        cdEvent = findViewById(R.id.cd_event);
        bName = findViewById(R.id.b_name_event);
        bNode = findViewById(R.id.b_node_event);
        bTag = findViewById(R.id.b_tag_event);
        n1 = findViewById(R.id.n_1_event);
        n2 = findViewById(R.id.n_2_event);
        n3 = findViewById(R.id.n_3_event);
        n4 = findViewById(R.id.n_4_event);
        n5 = findViewById(R.id.n_5_event);
        n6 = findViewById(R.id.n6_event);
        n7 = findViewById(R.id.n_7_event);
        c1 = findViewById(R.id.c_1_event);
        c2 = findViewById(R.id.c_2_event);
        c3 = findViewById(R.id.c_3_event);
        c4 = findViewById(R.id.c_4_event);
        c5 = findViewById(R.id.c_5_event);
        c6 = findViewById(R.id.c_6_event);
        c7 = findViewById(R.id.c_7_event);
        closeCard = findViewById(R.id.btn_cls_card_event);

        cardView.setVisibility(View.INVISIBLE);

        // TODO : hilangkan beacon
        pinUserThree.setVisibility(View.GONE);
        pinUserTwo.setVisibility(View.GONE);

        audioOn();

        // Beacon Information
        beaconInfo();

        searchMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userPos == 0){
                    Toast.makeText(EventActivity.this, "please enter the beacon area", Toast.LENGTH_SHORT).show();
                }else {
                    Intent searchIntent = new Intent(EventActivity.this,ChooseRoomActivity.class);
                    searchIntent.putExtra("user pos",userPos);
                    startActivity(searchIntent);
                }
            }
        });

        event_tap = "out";

        String a = getIntent().getStringExtra("beacon");
        if (a==null){
            Toast.makeText(EventActivity.this, "Gaada", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(EventActivity.this, ""+a, Toast.LENGTH_SHORT).show();
        }

        cdEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (event_tap.equalsIgnoreCase("out")){
                    Toast.makeText(EventActivity.this, "Outside Beacon", Toast.LENGTH_SHORT).show();
                }else if (event_tap.equalsIgnoreCase(BLUEBERRY)){
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID,2);
                    startActivity(intent);
                }else if (event_tap.equalsIgnoreCase(COCONUT)){
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID,3);
                    startActivity(intent);
                }
            }
        });


        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                startEstimote();
                                return null;
                            }
                        },
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });

    }

    private void beaconInfo() {

        pinBcnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(1);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 1 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pinBcnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(2);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 2 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pinBcnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(3);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 3 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pinBcnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(4);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 4 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pinBcnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(5);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 5 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pinBcnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(6);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 6 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pinBcnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(7);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 7 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pinBcnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(8);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 8 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        closeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void startEstimote(){

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

        // Near Zone
        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("multiple-beacon-3o5")
                .inNearRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        String title = context.getAttachments().get("area");
                        if (title == null) {
                            title = "unknown";
                        }else if(title.equalsIgnoreCase(COCONUT)){
                            notificationManagaer.enterCoconut();
                            userPos = 3;
                            Toast.makeText(EventActivity.this, "coconut in 1 meter | Position : "+userPos, Toast.LENGTH_SHORT).show();
                            inBeacon(COCONUT);
                        }else if (title.equalsIgnoreCase(BLUEBERRY)){
                            notificationManagaer.enterBlueberry();
                            userPos = 2;
                            Toast.makeText(EventActivity.this, "blueberry in 1 meter | Position : "+userPos, Toast.LENGTH_SHORT).show();
                            inBeacon(BLUEBERRY);

                        }
                        // TODO : posisi user
                        userPosition(userPos);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        String title = proximityContext.getAttachments().get("area");
                        if (title == null) {
                            title = "unknown";
                        }else if(title.equalsIgnoreCase(COCONUT)){
                            outBeacon();
                            Toast.makeText(EventActivity.this, "coconut out", Toast.LENGTH_SHORT).show();
                        }else if (title.equalsIgnoreCase(BLUEBERRY)){
                            outBeacon();
                            Toast.makeText(EventActivity.this, "blueberry out", Toast.LENGTH_SHORT).show();
                        }
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(zone);
    }

    private void outBeacon(){
        // TODO :  keluar daribeacon hilangin
        pinUserThree.setVisibility(View.GONE);
        pinUserTwo.setVisibility(View.GONE);
        userPos = 0;
        noEvent.setText(getResources().getString(R.string.outside_beacon_area));
        noEvent.setTextColor(getResources().getColor(R.color.colorAccent));
        event_tap = "out";
    }

    private void inBeacon(String name){
        noEvent.setText(getResources().getString(R.string.inside_beacon_area));
        noEvent.setTextColor(getResources().getColor(R.color.deep_blue));
        if (name.equalsIgnoreCase(COCONUT)){
            event_tap = COCONUT;

        }else if (name.equalsIgnoreCase(BLUEBERRY)){
            event_tap = BLUEBERRY;
        }
    }

    private void audioOn() {
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

    public void gson(int numberBeacon){

        switch (numberBeacon){
            case 1:
                String jsonFileStringBeacon01 = Utils.getJsonFromAssets(getApplicationContext(), "beacon01.json");
                Log.i("data", jsonFileStringBeacon01);

                Gson gsonBeacon01 = new Gson();
                Beacon beacons1 = gsonBeacon01.fromJson(jsonFileStringBeacon01, Beacon.class);

                bName.setText(beacons1.getInformation().getName());
                bTag.setText(beacons1.getInformation().getTags());
                bNode.setText(beacons1.getInformation().getNode());
                n1.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(3));
                n5.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(4));
                n6.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(5));
                n7.setText(beacons1.getAlgorithm().get(0).getNeightbor().get(6));
                c1.setText(beacons1.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons1.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons1.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText(beacons1.getAlgorithm().get(0).getCost().get(3).toString()+" m");
                c5.setText(beacons1.getAlgorithm().get(0).getCost().get(4).toString()+" m");
                c6.setText(beacons1.getAlgorithm().get(0).getCost().get(5).toString()+" m");
                c7.setText(beacons1.getAlgorithm().get(0).getCost().get(6).toString()+" m");
                break;
            case 2:
                String jsonFileStringBlueberry = Utils.getJsonFromAssets(getApplicationContext(), "blueberry.json");
                Log.i("data", jsonFileStringBlueberry);

                Gson gsonBlueberry = new Gson();
                Beacon beacons2 = gsonBlueberry.fromJson(jsonFileStringBlueberry, Beacon.class);

                bName.setText(beacons2.getInformation().getName());
                bTag.setText(beacons2.getInformation().getTags());
                bNode.setText(beacons2.getInformation().getNode());
                n1.setText(beacons2.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons2.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons2.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText(beacons2.getAlgorithm().get(0).getNeightbor().get(3));
                n5.setText(beacons2.getAlgorithm().get(0).getNeightbor().get(4));
                n6.setText("");
                n7.setText("");
                c1.setText(beacons2.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons2.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons2.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText(beacons2.getAlgorithm().get(0).getCost().get(3).toString()+" m");
                c5.setText(beacons2.getAlgorithm().get(0).getCost().get(4).toString()+" m");
                c6.setText("");
                c7.setText("");
                break;
            case 3:
                String jsonFileStringCoconut = Utils.getJsonFromAssets(getApplicationContext(), "coconut.json");
                Log.i("data", jsonFileStringCoconut);

                Gson gsonCoconut = new Gson();
                Beacon beacons3 = gsonCoconut.fromJson(jsonFileStringCoconut, Beacon.class);

                bName.setText(beacons3.getInformation().getName());
                bTag.setText(beacons3.getInformation().getTags());
                bNode.setText(beacons3.getInformation().getNode());
                n1.setText(beacons3.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons3.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons3.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText("");
                n5.setText("");
                n6.setText("");
                n7.setText("");
                c1.setText(beacons3.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons3.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons3.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText("");
                c5.setText("");
                c6.setText("");
                c7.setText("");
                break;
            case 4:
                String jsonFileStringBeacon04 = Utils.getJsonFromAssets(getApplicationContext(), "beacon04.json");
                Log.i("data", jsonFileStringBeacon04);

                Gson gsonBeacon04 = new Gson();
                Beacon beacons4 = gsonBeacon04.fromJson(jsonFileStringBeacon04, Beacon.class);

                bName.setText(beacons4.getInformation().getName());
                bTag.setText(beacons4.getInformation().getTags());
                bNode.setText(beacons4.getInformation().getNode());
                n1.setText(beacons4.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons4.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons4.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText(beacons4.getAlgorithm().get(0).getNeightbor().get(3));
                n5.setText("");
                n6.setText("");
                n7.setText("");
                c1.setText(beacons4.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons4.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons4.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText(beacons4.getAlgorithm().get(0).getCost().get(3).toString()+" m");
                c5.setText("");
                c6.setText("");
                c7.setText("");
                break;
            case 5:
                String jsonFileStringBeacon05 = Utils.getJsonFromAssets(getApplicationContext(), "beacon05.json");
                Log.i("data", jsonFileStringBeacon05);

                Gson gsonBeacon05 = new Gson();
                Beacon beacons5 = gsonBeacon05.fromJson(jsonFileStringBeacon05, Beacon.class);

                bName.setText(beacons5.getInformation().getName());
                bTag.setText(beacons5.getInformation().getTags());
                bNode.setText(beacons5.getInformation().getNode());
                n1.setText(beacons5.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons5.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons5.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText(beacons5.getAlgorithm().get(0).getNeightbor().get(3));
                n5.setText(beacons5.getAlgorithm().get(0).getNeightbor().get(4));
                n6.setText("");
                n7.setText("");
                c1.setText(beacons5.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons5.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons5.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText(beacons5.getAlgorithm().get(0).getCost().get(3).toString()+" m");
                c5.setText(beacons5.getAlgorithm().get(0).getCost().get(4).toString()+" m");
                c6.setText("");
                c7.setText("");
                break;
            case 6:
                String jsonFileStringBeacon06 = Utils.getJsonFromAssets(getApplicationContext(), "beacon06.json");
                Log.i("data", jsonFileStringBeacon06);

                Gson gsonBeacon06 = new Gson();
                Beacon beacons6 = gsonBeacon06.fromJson(jsonFileStringBeacon06, Beacon.class);

                bName.setText(beacons6.getInformation().getName());
                bTag.setText(beacons6.getInformation().getTags());
                bNode.setText(beacons6.getInformation().getNode());
                n1.setText(beacons6.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons6.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons6.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText(beacons6.getAlgorithm().get(0).getNeightbor().get(3));
                n5.setText(beacons6.getAlgorithm().get(0).getNeightbor().get(4));
                n6.setText("");
                n7.setText("");
                c1.setText(beacons6.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons6.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons6.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText(beacons6.getAlgorithm().get(0).getCost().get(3).toString()+" m");
                c5.setText(beacons6.getAlgorithm().get(0).getCost().get(4).toString()+" m");
                c6.setText("");
                c7.setText("");
                break;
            case 7:
                String jsonFileStringBeacon07 = Utils.getJsonFromAssets(getApplicationContext(), "beacon07.json");
                Log.i("data", jsonFileStringBeacon07);

                Gson gsonBeacon07 = new Gson();
                Beacon beacons7 = gsonBeacon07.fromJson(jsonFileStringBeacon07, Beacon.class);

                bName.setText(beacons7.getInformation().getName());
                bTag.setText(beacons7.getInformation().getTags());
                bNode.setText(beacons7.getInformation().getNode());
                n1.setText(beacons7.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons7.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons7.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText(beacons7.getAlgorithm().get(0).getNeightbor().get(3));
                n5.setText(beacons7.getAlgorithm().get(0).getNeightbor().get(4));
                n6.setText(beacons7.getAlgorithm().get(0).getNeightbor().get(5));
                n7.setText("");
                c1.setText(beacons7.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons7.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons7.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText(beacons7.getAlgorithm().get(0).getCost().get(3).toString()+" m");
                c5.setText(beacons7.getAlgorithm().get(0).getCost().get(4).toString()+" m");
                c6.setText(beacons7.getAlgorithm().get(0).getCost().get(5).toString()+" m");
                c7.setText("");
                break;
            case 8:
                String jsonFileStringBeacon08 = Utils.getJsonFromAssets(getApplicationContext(), "beacon08.json");
                Log.i("data", jsonFileStringBeacon08);

                Gson gsonBeacon08 = new Gson();
                Beacon beacons8 = gsonBeacon08.fromJson(jsonFileStringBeacon08, Beacon.class);

                bName.setText(beacons8.getInformation().getName());
                bTag.setText(beacons8.getInformation().getTags());
                bNode.setText(beacons8.getInformation().getNode());
                n1.setText(beacons8.getAlgorithm().get(0).getNeightbor().get(0));
                n2.setText(beacons8.getAlgorithm().get(0).getNeightbor().get(1));
                n3.setText(beacons8.getAlgorithm().get(0).getNeightbor().get(2));
                n4.setText("");
                n5.setText("");
                n6.setText("");
                n7.setText("");
                c1.setText(beacons8.getAlgorithm().get(0).getCost().get(0).toString()+" m");
                c2.setText(beacons8.getAlgorithm().get(0).getCost().get(1).toString()+" m");
                c3.setText(beacons8.getAlgorithm().get(0).getCost().get(2).toString()+" m");
                c4.setText("");
                c5.setText("");
                c6.setText("");
                c7.setText("");
                break;
        }

    }

    private void userPosition(int pos){
        pinUserThree.setVisibility(View.GONE);
        pinUserTwo.setVisibility(View.GONE);
        switch (pos){
            case 2:
                pinUserTwo.setVisibility(View.VISIBLE);
                break;
            case 3:
                pinUserThree.setVisibility(View.VISIBLE);
                break;
        }
    }
}
