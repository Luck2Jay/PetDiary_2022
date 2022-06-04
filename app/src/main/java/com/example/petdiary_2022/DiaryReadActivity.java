package com.example.petdiary_2022;



import static com.example.petdiary_2022.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiaryReadActivity extends AppCompatActivity {

    Diary diary;
    ListView listview;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    TextView diary_name, diary_id, diary_date, diarytv;
    EditText del_pw;
    Button btn_diary_del;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_read);

        Intent intent = getIntent();
        diary = (Diary) intent.getSerializableExtra("date");

        diary_name = findViewById(R.id.diary_name);
        diary_name.setText(diary.name);

        diary_id = findViewById(R.id.diary_id);
        diary_id.setText(diary.id);

        diary_date = findViewById(R.id.diary_date);
        diary_date.setText(diary.date);

        diarytv = findViewById(R.id.diary);
        diarytv.setText(diary.text);



        btn_diary_del = findViewById(R.id.diary_post_del);
        btn_diary_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delDiary();
                DiaryReadActivity.super.onRestart();
                finish();
            }
        });

    }


    public void delDiary(){
        databaseReference.child(user.id).child("Diary").child(diary.name).setValue(null);
    }



}