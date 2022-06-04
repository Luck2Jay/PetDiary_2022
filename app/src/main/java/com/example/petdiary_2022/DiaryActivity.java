package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

public class DiaryActivity extends AppCompatActivity {

    Button btn_diary_write ;
    ListView listView;
    DiaryAdapter adapter;
    List<Diary> diary = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference diaryRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        database = FirebaseDatabase.getInstance();
        String temp_path = user.id+"/Diary";
        diaryRef = database.getReference(temp_path);
        diaryRef.addValueEventListener(postListener);

        listView = findViewById(R.id.diaryListView);
        adapter = new DiaryAdapter(diary,this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),DiaryReadActivity.class);
                Object object = adapterView.getAdapter().getItem(i);
//                intent.putExtra("date", (Bundle) object);
                intent.putExtra("date", (Diary) object);
                startActivity(intent);
            }
        });


        btn_diary_write = findViewById(R.id.btn_diary_write);
        btn_diary_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiaryWriteActivity.class);
                startActivity(intent);
            }
        });
    }


    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            diary.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String key = snapshot.getKey();
//                Revenue revenue = snapshot.getValue(Revenue.class);
//                revenue.date = key;
//                revenues.add(revenue);
//                if(key.contains(System.date)) {
                Diary item = snapshot.getValue(Diary.class);
                item.name = key; // 글 제목이 키
                diary.add(item);
            }
            listView.requestLayout(); //
            adapter.notifyDataSetChanged(); //
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}





