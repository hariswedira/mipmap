package org.d3ifcool.cubeacon.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.cubeacon.EventActivity;
import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.User;
import org.d3ifcool.cubeacon.utils.Constants;
import org.d3ifcool.cubeacon.utils.Preferences;

public class LoginActivity extends AppCompatActivity {

    private Button login, signup;
    private TextInputLayout password, username;
    private String sU, sP;
    private int iUsername, iPassword;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reference = FirebaseDatabase.getInstance().getReference("users");

        password = findViewById(R.id.text_input_password);
        username = findViewById(R.id.text_input_username);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput();
                if (iUsername == 1 && iPassword ==1){
                    signIn(username.getEditText().getText().toString(), password.getEditText().getText().toString());
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void confirmInput(){
        String sUsername = username.getEditText().getText().toString().trim();
        if (sUsername.isEmpty()){
            username.setError("Field can't be empty");
        }else {
            username.setError(null);
            iUsername = 1;
        }

        String sPassword = password.getEditText().getText().toString().trim();
        if (sPassword.isEmpty()){
            password.setError("Field can't be empty");
        }else {
            password.setError(null);
            iPassword = 1;
        }
    }

    public void signIn(String un, String ps){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(un).exists()){
                    User login = dataSnapshot.child(un).getValue(User.class);
                    if (login.getPassword().equalsIgnoreCase(ps)){
                        Preferences.save(getApplicationContext(), Constants.SESSION,"true");
                        Preferences.save(getApplicationContext(), Constants.USERNAME,login.getUsername());
                        Preferences.save(getApplicationContext(), Constants.PASSWORD,login.getPassword());
                        Intent loginIntent = new Intent(LoginActivity.this, EventActivity.class);
                        startActivity(loginIntent);
                        finish();
//                        Toast.makeText(LoginActivity.this, Preferences.read(getApplicationContext(),Constants.USERNAME,"u"), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "User don't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
