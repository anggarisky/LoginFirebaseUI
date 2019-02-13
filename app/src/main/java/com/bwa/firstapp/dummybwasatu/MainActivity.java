package com.bwa.firstapp.dummybwasatu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String usernameLocal = "bwa";
    Button btnContinue;
    EditText xusername, xpassword;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername();
            }
        });

    }

    public void checkUsername(){
        String getUsername = xusername.getText().toString();
        final String getPassword = xpassword.getText().toString();

        if(getUsername.isEmpty()){
            Toast.makeText(getApplicationContext(), "Username Kosong!", Toast.LENGTH_SHORT).show();
        }
        else {
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(getUsername);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String fromFirebasePassword = dataSnapshot.child("password").getValue().toString();
                        Toast.makeText(getApplicationContext(), "Current Password" + fromFirebasePassword, Toast.LENGTH_SHORT).show();
                        if(getPassword.equals(fromFirebasePassword)){
                            Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Salah password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Nothing!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
