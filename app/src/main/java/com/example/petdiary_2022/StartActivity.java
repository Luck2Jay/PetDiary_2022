package com.example.petdiary_2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_login = findViewById(R.id.BtnLogin);
        btn_register = findViewById(R.id.BtnRegister);


        btn_login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        });

        btn_register.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }



}