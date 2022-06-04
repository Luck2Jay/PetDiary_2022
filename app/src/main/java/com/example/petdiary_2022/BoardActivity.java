package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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

// 커뮤니티 시작 화면, 리스트 뷰로 글 목록 보여주고 선택할 수 있는 화면
public class BoardActivity extends AppCompatActivity {

    Button btn_write ;
    ListView listView;
    BoardAdapter adapter;
    List<Board> board = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference writeRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        database = FirebaseDatabase.getInstance();

        writeRef = database.getReference("Community"); //User1/GPS
        writeRef.addValueEventListener(postListener);

        listView = findViewById(R.id.communityListView);
        adapter = new BoardAdapter(board,this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),BoardReadActivity.class);
                Object object = adapterView.getAdapter().getItem(i);
//                intent.putExtra("date", (Bundle) object);
                intent.putExtra("date", (Board) object);
                startActivity(intent);
            }
        });


        // 글 작성 버튼 누르면 이동하는 버튼
        btn_write = findViewById(R.id.btn_write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardWriteActivity.class);
                startActivity(intent);
            }
        });
    }


    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            board.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String key = snapshot.getKey();
//                Revenue revenue = snapshot.getValue(Revenue.class);
//                revenue.date = key;
//                revenues.add(revenue);
//                if(key.contains(System.date)) {
                Board item = snapshot.getValue(Board.class);
                item.name = key; // 글 제목이 키
                board.add(item);
            }
            listView.requestLayout(); //
            adapter.notifyDataSetChanged(); //
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };




}