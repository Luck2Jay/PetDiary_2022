package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryWriteActivity  extends AppCompatActivity {

    Button btn_enroll;
    EditText diary_name, diary_text;
    String user_id = user.id;
    String date;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        String temp = getTime();
        date = temp;

        diary_name = findViewById(R.id.diary_name_edit);
        diary_text = findViewById(R.id.diary_et);


        btn_enroll = findViewById(R.id.btn_diary_enroll);
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWrite(user_id,diary_name.getText().toString(),diary_text.getText().toString(),date);
                DiaryWriteActivity.super.onRestart();
                finish();

            }
        });



    }

    public void addWrite(String id, String name, String text, String date){ // 새로운 과제 체크 리스트 만드는 것
        Diary diary = new Diary(id,name,text,date);
        databaseReference.child(id).child("Diary").child(name).setValue(diary);

    }

    private String getTime() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm");
        String getTime = dateFormat.format(date);

        return getTime;
    }



}