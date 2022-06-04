package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {

    TextView id, pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        id = findViewById(R.id.id_tv_input);
        id.setText(user.id);

        pw = findViewById(R.id.pw_tv_input);
        pw.setText(user.pw);

    }
}