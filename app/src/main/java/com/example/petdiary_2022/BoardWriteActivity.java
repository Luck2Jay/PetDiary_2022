package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardWriteActivity extends AppCompatActivity {

    Button btn_en;
    EditText tn, ti;
    String user_id = user.id;
    String date;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        String temp = getTime();
        date = temp;

        tn = findViewById(R.id.textname_input);
        ti = findViewById(R.id.text_input);


        btn_en = findViewById(R.id.btn_enroll);
        btn_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWrite(user_id,tn.getText().toString(),ti.getText().toString(),date);
               BoardWriteActivity.super.onRestart();
                finish();

            }
        });



    }

    public void addWrite(String id, String name, String text, String date){ // 새로운 과제 체크 리스트 만드는 것
        Board board = new Board(id,name,text,date);
        databaseReference.child("Community").child(name).setValue(board);

    }

    private String getTime() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm");
        String getTime = dateFormat.format(date);

        return getTime;
    }



}
