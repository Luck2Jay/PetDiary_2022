package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class PathChoiceActivity extends AppCompatActivity {

    ListView listview;
    PathAdapter adapter;
    List<Path> path = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference gpsRef;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_choice);

//        String id = user.getID();
//        String path1 = id + "/GPS";

        database = FirebaseDatabase.getInstance();
        String temp_path = user.id+"/GPS";
//        gpsRef = database.getReference("User1/GPS"); //User1/GPS
        gpsRef = database.getReference(temp_path); //User1/GPS
        gpsRef.addValueEventListener(postListener);

        listview = findViewById(R.id.pathListView);
        adapter = new PathAdapter(path,this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                    Intent intent = new Intent(getApplicationContext(),OverlayMapActivity.class);
                    Object object = adapterView.getAdapter().getItem(i);
//                intent.putExtra("date", (Bundle) object);
                    intent.putExtra("date", (Path) object);
                    startActivity(intent);
            }
        });


    }


    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            path.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String key = snapshot.getKey();
//                Revenue revenue = snapshot.getValue(Revenue.class);
//                revenue.date = key;
//                revenues.add(revenue);
//                if(key.contains(System.date)) {
                Path item = snapshot.getValue(Path.class);
                item.name = key;
                path.add(item);
//                }
            }
            listview.requestLayout(); //
            adapter.notifyDataSetChanged(); //
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}