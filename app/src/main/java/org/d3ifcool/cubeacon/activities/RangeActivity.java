package org.d3ifcool.cubeacon.activities;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import org.d3ifcool.cubeacon.EventActivity;
import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.estimote.EstimoteUtils;
import org.d3ifcool.cubeacon.estimote.ProximityContent;
import org.d3ifcool.cubeacon.estimote.ProximityContentAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RangeActivity extends AppCompatActivity {

    private GridView rangeGrid;
    private ProximityContentAdapter proximityContentAdapter;
    private ProximityObserver.Handler proximityObserverHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);

        proximityContentAdapter = new ProximityContentAdapter(this);
        rangeGrid = findViewById(R.id.grid_range);
        rangeGrid.setAdapter(proximityContentAdapter);

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                startProximityContentManager();
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

    private void startProximityContentManager() {
        EventActivity eventActivity = new EventActivity();

        ProximityObserver proximityObserver = new ProximityObserverBuilder(getApplicationContext(), eventActivity.cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();

        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("mipmap-hqh")
                .inCustomRange(3.0)
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> contexts) {

                        List<ProximityContent> nearbyContent = new ArrayList<>(contexts.size());

                        for (ProximityZoneContext proximityContext : contexts) {
                            String title = proximityContext.getAttachments().get("mipmap-hqh/title");
                            if (title == null) {
                                title = "unknown";
                            }
                            String subtitle = EstimoteUtils.getShortIdentifier(proximityContext.getDeviceId());

                            nearbyContent.add(new ProximityContent(title, subtitle));
                        }

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

        proximityObserverHandler = proximityObserver.startObserving(zone);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        proximityObserverHandler.stop();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        proximityObserverHandler.stop();
//    }
}
