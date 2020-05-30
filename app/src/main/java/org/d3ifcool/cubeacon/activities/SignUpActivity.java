package org.d3ifcool.cubeacon.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.User;
import org.d3ifcool.cubeacon.utils.Constants;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("users");
    private TextInputLayout username, password;
    private Button signup;
    private int iUsername, iPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.signup_username_field);
        password = findViewById(R.id.signup_password_field);
        signup = findViewById(R.id.btn_signup_user);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput();
                User user = new User(username.getEditText().getText().toString().trim(),password.getEditText().getText().toString().trim(),0);
                if (iUsername == 1 && iPassword == 1){
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getUsername()).exists()){
                                Toast.makeText(SignUpActivity.this, "User Exist", Toast.LENGTH_SHORT).show();
                            }else {
                                reference.child(user.getUsername()).setValue(user);
                                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
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
}
