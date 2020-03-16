package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.d3ifcool.cubeacon.estimote.ProximityContentManager;

import java.util.List;

public class EventActivity extends AppCompatActivity {

    public EstimoteCloudCredentials cloudCredentials =
        new EstimoteCloudCredentials("febbydahlan034-gmail-com-s-6wz", "93eb2e64e84caf1d30079ad3c7b8b7e8");
    private NotificationManagaer notificationManagaer;

    private int userPos;
    private TextView ruangan, judul, desk, tgl, noEvent;
    private ImageView photoEvent;


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

        photoEvent.setVisibility(View.INVISIBLE);


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
                        }else if (title.equalsIgnoreCase("blueberry")){
                            Toast.makeText(EventActivity.this, "blueberry out", Toast.LENGTH_SHORT).show();
                            outBeacon();
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
        noEvent.setText("You're outside the beacon area");
        photoEvent.setVisibility(View.INVISIBLE);
    }

    private void inBeacon(String name){
        if (name.equalsIgnoreCase("coconut")){
            ruangan.setText("MP Mart");
            judul.setText("Discount 50% All Item");
            desk.setText("Potongan harga untuk semua barang khusus untuk hari ini saja");
            tgl.setText("20 Maret 2020");
            noEvent.setText("");
            photoEvent.setVisibility(View.VISIBLE);
        }else if (name.equalsIgnoreCase("blueberry")){
            ruangan.setText("Laboran");
            judul.setText("Workshop Android Expert");
            desk.setText("Belajar MVVM + Live Data, Room, Retrofit dan RecyclerView");
            tgl.setText("22 Maret 2020");
            noEvent.setText("");
            photoEvent.setVisibility(View.VISIBLE);
        }
    }

}
