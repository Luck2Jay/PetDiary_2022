package com.example.petdiary_2022;


import static com.example.petdiary_2022.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ImageButton BtnRecord;
    ImageButton BtnBoard;
    ImageButton BtnPhoto;
    ImageButton BtnDiary;
    ImageButton BtnStart;
    ImageButton BtnHospital;
    ImageButton BtnMyPage;
    ImageButton BtnCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BtnRecord = findViewById(R.id.BtnRecord);
        BtnBoard = findViewById(R.id.BtnBoard);
        BtnStart = findViewById(R.id.BtnSanStart);
        BtnDiary = findViewById(R.id.BtnDiary);
        BtnPhoto = findViewById(R.id.BtnPhoto);
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

        BtnDiary.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DiaryActivity.class);
                startActivity(intent);
            }
        });

        BtnStart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GpsRecordActivity.class);
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


        BtnPhoto.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                startActivity(intent);
            }
        });



//        BtnLost.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LostActivity.class);
//                startActivity(intent);
//            }
//        });

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
                Intent intent = new Intent(getApplicationContext(),PathChoiceActivity.class);
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
