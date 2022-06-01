package com.example.petdiary_2022;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.ArrowheadPathOverlay;
import com.naver.maps.map.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OverlayMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    private MapView mapView;
    private static NaverMap naverMap;

    private LatLng myLatLng = new LatLng(37.3399, 126.733);
    private List<LatLng> firbase_latlng = new ArrayList<>();

    Path path;
    int s_time; // 산책 시작 시간
    int e_time; // 산책 종료 시간
    int date; // 산책 날짜
    float speed; // 산책 속도

    // 파이어베이스 연결 위해 사용
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathmap);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent intent = getIntent();
        path = (Path) intent.getSerializableExtra("date");

        LatLng temp_lat = new LatLng(0, 0); // 좌표 계산에 잠시 사용할 임시용


        for(int i=0;i<path.list.size();i++){
            String temp = path.list.get(i);
            String tmp[] = temp.split(",");
            if((tmp[3]==null)) continue;
            else {
                temp_lat = new LatLng(Latitude2Decimal(tmp[3], tmp[4]), Longitude2Decimal(tmp[5], tmp[6]));
                firbase_latlng.add(temp_lat);
            }
        }

    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        ArrowheadPathOverlay arrowheadPath = new ArrowheadPathOverlay();
        arrowheadPath.setCoords(firbase_latlng);
        arrowheadPath.setWidth(20);
        arrowheadPath.setHeadSizeRatio(3);
        arrowheadPath.setColor(Color.DKGRAY);
        arrowheadPath.setOutlineColor(Color.BLUE);

        arrowheadPath.setMap(naverMap);

//
//        PathOverlay path = new PathOverlay();
//        path.setCoords(firbase_latlng);
//        path.setWidth(15);
//        path.setColor(Color.GREEN);
//        path.setMap(naverMap);

//        CameraPosition cameraPosition = new CameraPosition(myLatLng, 16);
//        naverMap.setCameraPosition(cameraPosition);
    }


    // utils
    static float Latitude2Decimal(String lat, String NS) {
        float med = Float.parseFloat(lat.substring(2))/60.0f;
        med +=  Float.parseFloat(lat.substring(0, 2));
        if(NS.startsWith("S")) {
            med = -med;
        }
        return med;
    }

    static float Longitude2Decimal(String lon, String WE) {
        float med = Float.parseFloat(lon.substring(3))/60.0f;
        med +=  Float.parseFloat(lon.substring(0, 3));
        if(WE.startsWith("W")) {
            med = -med;
        }
        return med;
    }

}
