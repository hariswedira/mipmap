package org.d3ifcool.cubeacon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.d3ifcool.cubeacon.activities.AboutActivity;
import org.d3ifcool.cubeacon.activities.ListEventActivity;
import org.d3ifcool.cubeacon.activities.LoginActivity;
import org.d3ifcool.cubeacon.activities.ProfileActivity;
import org.d3ifcool.cubeacon.models.Beacon;
import org.d3ifcool.cubeacon.models.Event;
import org.d3ifcool.cubeacon.utils.Constants;
import org.d3ifcool.cubeacon.utils.Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EventActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, SensorEventListener {

    public EstimoteCloudCredentials cloudCredentials =
            new EstimoteCloudCredentials("mipmap-hqh", "6756bb70e7d65c3bd6a367882450915a");
    //        new EstimoteCloudCredentials("febbydahlan034-gmail-com-s-6wz", "93eb2e64e84caf1d30079ad3c7b8b7e8");
    private NotificationManagaer notificationManagaer;
    private ProximityObserver.Handler proximityObserverHandler;

    private final String BLUEBERRY = "blueberry";
    private final String COCONUT = "coconut";
    private final String ICE = "ice";
    private final String MINT = "mint";

    private int userPos;
    private TextView noEvent;
    private ImageView signal, beaconLg, userPosIcon, searchMenu, pinUserOne, pinUserFour, pinUserTwo, pinUserThree, pinBcnTwo, pinBcnThree, pinBcnFour, pinBcnFive, pinBcnSix, pinBcnSeven, pinBcnEight, pinBcnOne, go;
    private TextView bName, bNode, bTag, n1, n2, n3, n4, n5, n6, n7, c1, c2, c3, c4, c5, c6, c7, amountEvent;
    private Button closeCard, seeDetail;
    private CardView cardView, cdEvent;
    private TextToSpeech textToSpeech;
    private Guideline guideline;

    public final String EVENT_ID = "event_id";
    private String event_tap;

    private ArrayList<Event> listEvent;

    NavigateActivity navigateActivity;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference dayCur = database.getReference("currentDay");

    // Compass
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currentAzimuth = 0f;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        userPos = 0;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        String users = Preferences.read(getApplicationContext(), Constants.USERNAME, "u");
        Toast.makeText(EventActivity.this, "Welcome "+users, Toast.LENGTH_SHORT).show();
        myRef = database.getReference().child("users").child(users).child("userPosition");

        navigateActivity = new NavigateActivity();

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        // start form sunday as 1
        dayCur.setValue(day);

        noEvent = findViewById(R.id.tv_no_event);
        searchMenu = findViewById(R.id.iv_search_room);
        pinUserTwo = findViewById(R.id.pin_user_pos_two);
        pinUserThree = findViewById(R.id.pin_user_pos_three);
        pinUserOne = findViewById(R.id.pin_user_pos_one);
        pinUserFour = findViewById(R.id.pin_user_pos_four);
        pinBcnTwo = findViewById(R.id.pin_bcn_two);
        pinBcnThree = findViewById(R.id.pin_bcn_three);
        pinBcnOne = findViewById(R.id.pin_bcn_one);
        pinBcnFour = findViewById(R.id.pin_bcn_four);
        pinBcnFive = findViewById(R.id.pin_bcn_five);
        pinBcnSix = findViewById(R.id.pin_bcn_seven);
        pinBcnSeven = findViewById(R.id.pin_bcn_six);
        pinBcnEight = findViewById(R.id.pin_bcn_eight);
        signal = findViewById(R.id.iv_signal);
        userPosIcon = findViewById(R.id.iv_user_pos);
        beaconLg = findViewById(R.id.iv_no_beacon_area);
        amountEvent = findViewById(R.id.amount_event);
        seeDetail = findViewById(R.id.btn_see_detail);

        // signal
//        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/signal.png?alt=media&token=b9ddb563-9be0-4722-a592-031d140f3254").into(signal);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/go_button.png?alt=media&token=c8f7901c-b910-41cc-a5f6-cf1923c59103").into(searchMenu);

        listEvent = new ArrayList<>();

        searchMenu.setVisibility(View.GONE);
        userPosIcon.setVisibility(View.GONE);
        noEvent.setVisibility(View.INVISIBLE);
        amountEvent.setVisibility(View.INVISIBLE);

        Preferences.save(getApplicationContext(), Constants.NOTIF_TWO, "false");

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
        pinUserOne.setVisibility(View.GONE);
        pinUserFour.setVisibility(View.GONE);
        pinUserThree.setVisibility(View.GONE);
        pinUserTwo.setVisibility(View.GONE);
        seeDetail.setVisibility(View.GONE);

        audioOn();

        // Beacon Information
        beaconInfo();

        userPosIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateUserPin(userPos);
            }
        });

        event_tap = "out";

        // Start Beacon
        createEstimote();

        signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(view);
            }
        });

        searchMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userPos == 0) {
                    Toast.makeText(EventActivity.this, "please enter the beacon area", Toast.LENGTH_SHORT).show();
                }
                if (userPos == 4) {
                    Toast.makeText(EventActivity.this, "unable to navigate", Toast.LENGTH_SHORT).show();
                } else {
                    Preferences.save(getApplicationContext(), Constants.NOTIF, "false");
                    Intent searchIntent = new Intent(EventActivity.this, ChooseRoomActivity.class);
                    searchIntent.putExtra("user pos", userPos);
                    startActivity(searchIntent);
                }
            }
        });

    }

    public void createEstimote() {
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

    private void startEstimote() {
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
                .forTag("mipmap-hqh")
                .inNearRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {

                        String title = context.getAttachments().get("area");
                        boolean notifUhuy = Boolean.valueOf(Preferences.read(getApplicationContext(), Constants.NOTIF, "false"));
                        boolean notifUhuys = Boolean.valueOf(Preferences.read(getApplicationContext(), Constants.NOTIF_TWO, "false"));
                        if (title == null) {
                            title = "unknown";
                        } else if (title.equalsIgnoreCase(COCONUT)) {
                            if (notifUhuy && notifUhuys) {
                                notificationManagaer.enterCoconut();
                            }
                            userPos = 3;
                            myRef.setValue(userPos);
                            Toast.makeText(EventActivity.this, "coconut in 1 meter | Position : " + userPos, Toast.LENGTH_SHORT).show();
                            inBeacon(COCONUT);
                        } else if (title.equalsIgnoreCase(BLUEBERRY)) {
                            if (notifUhuy && notifUhuys) {
                                notificationManagaer.enterBlueberry();
                            }
                            userPos = 2;
                            myRef.setValue(userPos);
                            Toast.makeText(EventActivity.this, "blueberry in 1 meter | Position : " + userPos, Toast.LENGTH_SHORT).show();
                            inBeacon(BLUEBERRY);

                        } else if (title.equalsIgnoreCase(MINT)) {
                            if (notifUhuy && notifUhuys) {
                                notificationManagaer.enterMint();
                            }
                            userPos = 1;
                            myRef.setValue(userPos);
                            Toast.makeText(EventActivity.this, "mint in 1 meter | Position : " + userPos, Toast.LENGTH_SHORT).show();
                            inBeacon(MINT);

                        } else if (title.equalsIgnoreCase(ICE)) {
                            if (notifUhuy && notifUhuys) {
                                notificationManagaer.enterIce();
                            }
                            userPos = 4;
                            myRef.setValue(userPos);
                            Toast.makeText(EventActivity.this, "ice in 1 meter | Position : " + userPos, Toast.LENGTH_SHORT).show();
                            inBeacon(ICE);

                        }
                        initDataEvent(userPos);
                        amountEvent.setVisibility(View.VISIBLE);
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
                        } else if (title.equalsIgnoreCase(COCONUT)) {
                            Toast.makeText(EventActivity.this, "coconut out", Toast.LENGTH_SHORT).show();
                            outBeacon();
                        } else if (title.equalsIgnoreCase(BLUEBERRY)) {
                            Toast.makeText(EventActivity.this, "blueberry out", Toast.LENGTH_SHORT).show();
                            outBeacon();
                        } else if (title.equalsIgnoreCase(ICE)) {
                            Toast.makeText(EventActivity.this, "ice out", Toast.LENGTH_SHORT).show();
                            outBeacon();
                        } else if (title.equalsIgnoreCase(MINT)) {
                            Toast.makeText(EventActivity.this, "mint out", Toast.LENGTH_SHORT).show();
                            outBeacon();
                        }

                        userPos = 0;
                        myRef.setValue(userPos);
                        return null;
                    }
                })
                .build();

        proximityObserverHandler = proximityObserver.startObserving(zone);
    }


    public void showPopUp(View v) {
        PopupMenu popupMenu = new PopupMenu(EventActivity.this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.signout:
                Toast.makeText(EventActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                // logout
                Preferences.save(this, Constants.SESSION, "false");
                myRef.setValue(0);
                Intent intentSession = new Intent(EventActivity.this, LoginActivity.class);
                startActivity(intentSession);
                finish();
                return true;
            case R.id.feedback:
                Intent intentProfile = new Intent(EventActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.about:
                Intent intent = new Intent(EventActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Preferences.save(getApplicationContext(), Constants.NOTIF_TWO, "true");
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        proximityObserverHandler.stop();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Preferences.save(getApplicationContext(), Constants.NOTIF, "true");
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

//        Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();
        Preferences.save(getApplicationContext(), Constants.NOTIF_TWO, "false");
        boolean a = getIntent().getBooleanExtra("beacon", false);

        cdEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.save(getApplicationContext(), Constants.NOTIF, "false");
                if (event_tap.equalsIgnoreCase("out")) {
                    Toast.makeText(EventActivity.this, getResources().getString(R.string.outside_beacon_area), Toast.LENGTH_SHORT).show();
                    int speech = textToSpeech.speak("Please go to beacon area", TextToSpeech.QUEUE_FLUSH, null);
                    animatePinBeacon();
                } else if (event_tap.equalsIgnoreCase(BLUEBERRY)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 2);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                } else if (event_tap.equalsIgnoreCase(COCONUT)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 3);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                } else if (event_tap.equalsIgnoreCase(ICE)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 4);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                } else if (event_tap.equalsIgnoreCase(MINT)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 1);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                }
            }
        });

        seeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.save(getApplicationContext(), Constants.NOTIF, "false");
                if (event_tap.equalsIgnoreCase(BLUEBERRY)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 2);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                } else if (event_tap.equalsIgnoreCase(COCONUT)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 3);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                } else if (event_tap.equalsIgnoreCase(ICE)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 4);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                } else if (event_tap.equalsIgnoreCase(MINT)) {
                    Intent intent = new Intent(EventActivity.this, ListEventActivity.class);
                    intent.putExtra(EVENT_ID, 1);
                    intent.putExtra("events", listEvent);
                    startActivity(intent);
                }
            }
        });
    }

    private void outBeacon() {
        // TODO :  keluar daribeacon hilangin
        seeDetail.setVisibility(View.GONE);
        beaconLg.setVisibility(View.VISIBLE);
        noEvent.setVisibility(View.INVISIBLE);
        amountEvent.setVisibility(View.INVISIBLE);
        searchMenu.setVisibility(View.GONE);
        userPosIcon.setVisibility(View.GONE);
        noEvent.setText(getResources().getString(R.string.enter_beacon_area));
        event_tap = "out";
    }

    private void inBeacon(String name) {
//        seeDetail.setVisibility(View.VISIBLE);

        beaconLg.setVisibility(View.INVISIBLE);
        noEvent.setVisibility(View.VISIBLE);
        searchMenu.setVisibility(View.VISIBLE);
        userPosIcon.setVisibility(View.VISIBLE);
        noEvent.setText(getResources().getString(R.string.inside_beacon_area));
        noEvent.setTextColor(getResources().getColor(R.color.deep_blue));
        if (name.equalsIgnoreCase(COCONUT)) {
            event_tap = COCONUT;
        } else if (name.equalsIgnoreCase(BLUEBERRY)) {
            event_tap = BLUEBERRY;
        } else if (name.equalsIgnoreCase(MINT)) {
            event_tap = MINT;
        } else if (name.equalsIgnoreCase(ICE)) {
            event_tap = ICE;
        }
    }

    private void audioOn() {
        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                        }
                    }
                });
    }

    private void userPosition(int pos) {
        pinUserOne.setVisibility(View.GONE);
        pinUserFour.setVisibility(View.GONE);
        pinUserThree.setVisibility(View.GONE);
        pinUserTwo.setVisibility(View.GONE);

        pinUserOne.clearAnimation();
        pinUserTwo.clearAnimation();
        pinUserThree.clearAnimation();
        pinUserFour.clearAnimation();

        switch (pos) {
            case 1:
                pinUserOne.setVisibility(View.VISIBLE);
                break;
            case 2:
                pinUserTwo.setVisibility(View.VISIBLE);
                break;
            case 3:
                pinUserThree.setVisibility(View.VISIBLE);
                break;
            case 4:
                pinUserFour.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void gson(int numberBeacon) {

        switch (numberBeacon) {
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
                c1.setText(beacons1.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons1.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons1.getAlgorithm().get(0).getCost().get(2).toString() + " m");
                c4.setText(beacons1.getAlgorithm().get(0).getCost().get(3).toString() + " m");
                c5.setText(beacons1.getAlgorithm().get(0).getCost().get(4).toString() + " m");
                c6.setText(beacons1.getAlgorithm().get(0).getCost().get(5).toString() + " m");
                c7.setText(beacons1.getAlgorithm().get(0).getCost().get(6).toString() + " m");
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
                c1.setText(beacons2.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons2.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons2.getAlgorithm().get(0).getCost().get(2).toString() + " m");
                c4.setText(beacons2.getAlgorithm().get(0).getCost().get(3).toString() + " m");
                c5.setText(beacons2.getAlgorithm().get(0).getCost().get(4).toString() + " m");
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
                c1.setText(beacons3.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons3.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons3.getAlgorithm().get(0).getCost().get(2).toString() + " m");
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
                c1.setText(beacons4.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons4.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons4.getAlgorithm().get(0).getCost().get(2).toString() + " m");
                c4.setText(beacons4.getAlgorithm().get(0).getCost().get(3).toString() + " m");
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
                c1.setText(beacons5.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons5.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons5.getAlgorithm().get(0).getCost().get(2).toString() + " m");
                c4.setText(beacons5.getAlgorithm().get(0).getCost().get(3).toString() + " m");
                c5.setText(beacons5.getAlgorithm().get(0).getCost().get(4).toString() + " m");
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
                c1.setText(beacons6.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons6.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons6.getAlgorithm().get(0).getCost().get(2).toString() + " m");
                c4.setText(beacons6.getAlgorithm().get(0).getCost().get(3).toString() + " m");
                c5.setText(beacons6.getAlgorithm().get(0).getCost().get(4).toString() + " m");
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
                c1.setText(beacons7.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons7.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons7.getAlgorithm().get(0).getCost().get(2).toString() + " m");
                c4.setText(beacons7.getAlgorithm().get(0).getCost().get(3).toString() + " m");
                c5.setText(beacons7.getAlgorithm().get(0).getCost().get(4).toString() + " m");
                c6.setText(beacons7.getAlgorithm().get(0).getCost().get(5).toString() + " m");
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
                c1.setText(beacons8.getAlgorithm().get(0).getCost().get(0).toString() + " m");
                c2.setText(beacons8.getAlgorithm().get(0).getCost().get(1).toString() + " m");
                c3.setText(beacons8.getAlgorithm().get(0).getCost().get(2).toString() + " m");
                c4.setText("");
                c5.setText("");
                c6.setText("");
                c7.setText("");
                break;
        }

    }

    private void beaconInfo() {

        pinBcnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(1);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 1 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        pinBcnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(2);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 2 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        pinBcnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(3);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 3 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        pinBcnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(4);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 4 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        pinBcnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(5);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 5 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        pinBcnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(6);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 6 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        pinBcnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(7);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 7 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        pinBcnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson(8);
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 8 information", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        closeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void animateUserPin(int pos) {
        switch (pos) {
            case 1:
                pinUserOne.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        pinUserOne.setScaleY(1f);
                        pinUserOne.setScaleX(1f);
                        pinUserOne.setAlpha(1f);
                    }
                });
                break;
            case 2:
                pinUserTwo.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        pinUserTwo.setScaleY(1f);
                        pinUserTwo.setScaleX(1f);
                        pinUserTwo.setAlpha(1f);
                    }
                });
                break;
            case 3:
                pinUserThree.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        pinUserThree.setScaleY(1f);
                        pinUserThree.setScaleX(1f);
                        pinUserThree.setAlpha(1f);
                    }
                });
                break;
            case 4:
                pinUserFour.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        pinUserFour.setScaleY(1f);
                        pinUserFour.setScaleX(1f);
                        pinUserFour.setAlpha(1f);
                    }
                });
                break;
        }
    }

    private void animatePinBeacon() {
        pinBcnOne.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnOne.setScaleY(1f);
                pinBcnOne.setScaleX(1f);
                pinBcnOne.setAlpha(1f);
            }
        });
        pinBcnTwo.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnTwo.setScaleY(1f);
                pinBcnTwo.setScaleX(1f);
                pinBcnTwo.setAlpha(1f);
            }
        });
        pinBcnThree.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnThree.setScaleY(1f);
                pinBcnThree.setScaleX(1f);
                pinBcnThree.setAlpha(1f);
            }
        });
        pinBcnFour.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnFour.setScaleY(1f);
                pinBcnFour.setScaleX(1f);
                pinBcnFour.setAlpha(1f);
            }
        });
        pinBcnFive.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnFive.setScaleY(1f);
                pinBcnFive.setScaleX(1f);
                pinBcnFive.setAlpha(1f);
            }
        });
        pinBcnSix.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnSix.setScaleY(1f);
                pinBcnSix.setScaleX(1f);
                pinBcnSix.setAlpha(1f);
            }
        });
        pinBcnSeven.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnSeven.setScaleY(1f);
                pinBcnSeven.setScaleX(1f);
                pinBcnSeven.setAlpha(1f);
            }
        });
        pinBcnEight.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                pinBcnEight.setScaleY(1f);
                pinBcnEight.setScaleX(1f);
                pinBcnEight.setAlpha(1f);
            }
        });
    }

    private void updateCardEvent() {
        if (listEvent.isEmpty()) {
            amountEvent.setTextColor(getResources().getColor(R.color.red));
            amountEvent.setText("No event at this time");
//            seeDetail.setVisibility(View.GONE);
        } else {
            seeDetail.setVisibility(View.VISIBLE);
            amountEvent.setTextColor(getResources().getColor(R.color.green));
            amountEvent.setText(listEvent.size() + " Event Found!");
        }
    }

    private void initDataEvent(int num) {
        if (num == 2) {
            DatabaseReference acara = database.getReference("event").child("bluberry");
            acara.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listEvent.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Event a = dataSnapshot1.getValue(Event.class);
                        listEvent.add(a);
                    }
                    updateCardEvent();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (num == 3) {
            DatabaseReference acara = database.getReference("event").child("coconut");
            acara.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listEvent.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Event a = dataSnapshot1.getValue(Event.class);
                        listEvent.add(a);
                    }
                    updateCardEvent();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (num == 1) {
            DatabaseReference acara = database.getReference("event").child("mint");
            acara.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listEvent.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Event a = dataSnapshot1.getValue(Event.class);
                        listEvent.add(a);
                    }
                    updateCardEvent();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (num == 4) {
            DatabaseReference acara = database.getReference("event").child("ice");
            acara.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listEvent.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Event a = dataSnapshot1.getValue(Event.class);
                        listEvent.add(a);
                    }
                    updateCardEvent();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
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

                if (userPos==1){
                    pinUserOne.startAnimation(anim);
                }else if (userPos==2){
                    pinUserTwo.startAnimation(anim);
                }else if (userPos==3){
                    pinUserThree.startAnimation(anim);
                }else if (userPos==4){
                    pinUserFour.startAnimation(anim);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
