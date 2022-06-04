package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoardReadActivity extends AppCompatActivity {

    Board board;
    ListView listview;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    TextView post_name, post_id, post_date, post;
    EditText reply, del_pw;
    Button btn_post_del;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        Intent intent = getIntent();
        board = (Board) intent.getSerializableExtra("date");

        post_name = findViewById(R.id.read_name);
        post_name.setText(board.name);

        post_id = findViewById(R.id.writer_id);
        post_id.setText(board.id);

        post_date = findViewById(R.id.read_date);
        post_date.setText(board.date);

        post = findViewById(R.id.post);
        post.setText(board.text);



        btn_post_del = findViewById(R.id.btn_post_del);
        btn_post_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delPost();
                BoardReadActivity.super.onRestart();
                finish();
            }
        });

    }



    public void delPost(){
       databaseReference.child("Community").child(board.name).setValue(null);

    }



}
