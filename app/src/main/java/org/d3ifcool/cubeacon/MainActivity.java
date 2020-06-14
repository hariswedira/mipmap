package org.d3ifcool.cubeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import org.d3ifcool.cubeacon.activities.LoginActivity;
import org.d3ifcool.cubeacon.utils.Constants;
import org.d3ifcool.cubeacon.utils.Preferences;

public class MainActivity extends AppCompatActivity{

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = pref.getBoolean("firststart", true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firstStart) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("firststart", false);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, ZoomActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    checkSession();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private void checkSession() {
        boolean session = Boolean.valueOf(Preferences.read(getApplicationContext(), Constants.SESSION,"false"));

        if (!session){
            Intent intentSession = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intentSession);
            finish();
        }else {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
//            Toast.makeText(this, "You Is Logged In", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
