package com.example.petdiary_2022;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ImageButton BtnRecord;
    ImageButton BtnBoard;
    ImageButton BtnStart;
    ImageButton BtnNameTag;
    ImageButton BtnLost;
    ImageButton BtnHospital;
    ImageButton BtnMyPage;
    ImageButton BtnCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BtnRecord = findViewById(R.id.BtnRecord);
        BtnBoard = findViewById(R.id.BtnBoard);
        BtnStart = findViewById(R.id.BtnStart);
        BtnNameTag = findViewById(R.id.Btnnametag);
        BtnLost = findViewById(R.id.BtnLost);
        BtnHospital = findViewById(R.id.BtnHospital);
        BtnMyPage = findViewById(R.id.BtnMyPage);
        BtnCafe = findViewById(R.id.BtnCafe);


        BtnRecord.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                startActivity(intent);
            }
        });

        BtnBoard.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        BtnStart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

        BtnNameTag.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NameTagActivity.class);
                startActivity(intent);
            }
        });

        BtnLost.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LostActivity.class);
                startActivity(intent);
            }
        });

        BtnHospital.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
                startActivity(intent);


            }
        });

        BtnCafe.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CafeActivity.class);
                startActivity(intent);
            }
        });

        BtnMyPage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });

    }

}
