package com.example.petdiary_2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


// 시작화면이다. 강아지 사진 나오는
public class MainActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_register;
    EditText et_id,et_pw;

    static User user;

    // Write a message to the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.BtnLogin);
        btn_register = findViewById(R.id.BtnRegister);
        et_id = findViewById(R.id.EditID);
        et_pw = findViewById(R.id.EditPW);

        btn_login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                user = new User(et_id.getText().toString(),et_pw.getText().toString());
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}