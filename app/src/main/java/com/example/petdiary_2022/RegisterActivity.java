package com.example.petdiary_2022;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// 회원가입 및 등록하는 파일
public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    Button btn;
    EditText edit1, edit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = findViewById(R.id.btn);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(edit1.getText().toString(),edit2.getText().toString());
            }
        });
    }

    public void addUser(String id, String pw) {
        User user = new User(id,pw);
      databaseReference.child(id).child("회원정보").setValue(user); // 추후 수정 필요1!!!!!
    }
}
