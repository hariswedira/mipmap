package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import java.util.List;
import java.util.Locale;

public class NavigateActivity extends AppCompatActivity {

    private ImageView userIcon;
    Button up;
    Animation aU;

    private int currentPos;
    private TextToSpeech textToSpeech;

    // estimote
    private NotificationManagaer notificationManagaer;
    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("febbydahlan034-gmail-com-s-6wz", "93eb2e64e84caf1d30079ad3c7b8b7e8");

    // node beacon
    private int[] beaconNode = {1,2,3,4,5,6,7,8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        userIcon = findViewById(R.id.node_user);
        userIcon.setVisibility(View.INVISIBLE);
        up = findViewById(R.id.btn_up);

        aU = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userIcon.startAnimation(aU);

                int speech = textToSpeech.speak("uhuy",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        // Estimote
        estimoteUhuy();

        // text to speech
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

    private void estimoteUhuy() {
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

        // Create the Proximity Zone
        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("node-blueberry")
                .inCustomRange(1.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        currentPos = 1;
                        notificationManagaer.enterBlueberry();
                        Toast.makeText(NavigateActivity.this, "Node "+currentPos, Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.VISIBLE);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManagaer.exitBlueberry();
                        Toast.makeText(NavigateActivity.this, "Exit Blueberry", Toast.LENGTH_SHORT).show();
                        userIcon.setVisibility(View.INVISIBLE);
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(zone);


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
