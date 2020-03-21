package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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

import org.d3ifcool.cubeacon.estimote.ProximityContentManager;

import java.util.List;
import java.util.Locale;

public class EventActivity extends AppCompatActivity {

    public EstimoteCloudCredentials cloudCredentials =
        new EstimoteCloudCredentials("febbydahlan034-gmail-com-s-6wz", "93eb2e64e84caf1d30079ad3c7b8b7e8");
    private NotificationManagaer notificationManagaer;

    private int userPos;
    private TextView ruangan, judul, desk, tgl, noEvent;
    private ImageView photoEvent, searchMenu, pinUser, pinBcnTwo, pinBcnThree;
    TextView bName, bNode, bTag, n1, n2, n3, n4, n5, c1, c2, c3, c4, c5;
    Button closeCard;
    CardView cardView, cdEvent;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        userPos = 0;
        ruangan = findViewById(R.id.tv_room_event);
        judul = findViewById(R.id.tv_title_event);
        desk = findViewById(R.id.tv_desc_event);
        tgl = findViewById(R.id.tv_date_event);
        photoEvent = findViewById(R.id.iv_event);
        noEvent = findViewById(R.id.tv_no_event);
        searchMenu = findViewById(R.id.iv_search_room);
        pinUser = findViewById(R.id.pin_user_pos);
        pinBcnTwo = findViewById(R.id.pin_bcn_two);
        pinBcnThree = findViewById(R.id.pin_bcn_three);

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
        c1 = findViewById(R.id.c_1_event);
        c2 = findViewById(R.id.c_2_event);
        c3 = findViewById(R.id.c_3_event);
        c4 = findViewById(R.id.c_4_event);
        c5 = findViewById(R.id.c_5_event);
        closeCard = findViewById(R.id.btn_cls_card_event);

        photoEvent.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);

        audioOn();

        pinBcnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson();
                cardView.setVisibility(View.VISIBLE);
                int speech = textToSpeech.speak("display beacon 1 information",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        closeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.INVISIBLE);
            }
        });


        searchMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(EventActivity.this,NavigateActivity.class);
                startActivity(searchIntent);
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
                        }else if(title.equalsIgnoreCase("coconut")){
                            notificationManagaer.enterCoconut();
                            userPos = 1;
                            Toast.makeText(EventActivity.this, "coconut in 1 meter | Position : "+userPos, Toast.LENGTH_SHORT).show();
                            inBeacon("coconut");
                        }else if (title.equalsIgnoreCase("blueberry")){
                            notificationManagaer.enterBlueberry();
                            userPos = 2;
                            Toast.makeText(EventActivity.this, "blueberry in 1 meter | Position : "+userPos, Toast.LENGTH_SHORT).show();
                            inBeacon("blueberry");

                        }
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        String title = proximityContext.getAttachments().get("area");
                        if (title == null) {
                            title = "unknown";
                        }else if(title.equalsIgnoreCase("coconut")){
                            Toast.makeText(EventActivity.this, "coconut out", Toast.LENGTH_SHORT).show();
                            outBeacon();
                            pinBcnThree.setImageResource(R.drawable.beacon);
                        }else if (title.equalsIgnoreCase("blueberry")){
                            Toast.makeText(EventActivity.this, "blueberry out", Toast.LENGTH_SHORT).show();
                            outBeacon();
                            pinBcnTwo.setImageResource(R.drawable.beacon);
                        }
                        ruangan.setText("");
                        judul.setText("");
                        desk.setText("");
                        tgl.setText("");
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(zone);
    }

    private void outBeacon(){
        pinUser.setVisibility(View.VISIBLE);
        noEvent.setText("You're outside the beacon area");
        photoEvent.setVisibility(View.INVISIBLE);
        cdEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EventActivity.this, "Outside Beacon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inBeacon(String name){
        pinUser.setVisibility(View.GONE);
        if (name.equalsIgnoreCase("coconut")){
            ruangan.setText("MP Mart");
            judul.setText("Discount 50% All Item");
            desk.setText("Potongan harga untuk semua barang khusus untuk hari ini saja");
            tgl.setText("20 Maret 2020");
            noEvent.setText("");
            photoEvent.setVisibility(View.VISIBLE);
            photoEvent.setImageResource(R.drawable.ic_launcher_background);
            pinBcnThree.setImageResource(R.drawable.user_pin);
            cdEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EventActivity.this, DetailRoomActivity.class);
                    intent.putExtra("title",judul.getText().toString());
                    intent.putExtra("date",tgl.getText().toString());
                    intent.putExtra("room",ruangan.getText().toString());
                    intent.putExtra("desc",desk.getText().toString());
                    intent.putExtra("photo",R.drawable.ic_launcher_foreground);
                    startActivity(intent);
                }
            });

        }else if (name.equalsIgnoreCase("blueberry")){
            ruangan.setText("Laboran");
            judul.setText("Workshop Android Expert");
            desk.setText("Belajar MVVM + Live Data, Room, Retrofit dan RecyclerView");
            tgl.setText("22 Maret 2020");
            noEvent.setText("");
            photoEvent.setVisibility(View.VISIBLE);
            photoEvent.setImageResource(R.drawable.icon_mipmap);
            pinBcnTwo.setImageResource(R.drawable.user_pin);
            cdEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EventActivity.this, DetailRoomActivity.class);
                    intent.putExtra("title",judul.getText().toString());
                    intent.putExtra("date",tgl.getText().toString());
                    intent.putExtra("room",ruangan.getText().toString());
                    intent.putExtra("desc",desk.getText().toString());
                    intent.putExtra("photo",R.drawable.icon_mipmap);
                    startActivity(intent);
                }
            });
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

    public void gson(){
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "beacon.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Beacon beacons1 = gson.fromJson(jsonFileString, Beacon.class);

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
}
