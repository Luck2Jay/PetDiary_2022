package com.example.petdiary_2022;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.ArrowheadPathOverlay;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.List;


// 이용 가능한 동물병원 마크업 페이지
public class HospitalActivity extends AppCompatActivity implements OnMapReadyCallback {


    private MapView mapView;
    private static NaverMap naverMap;

    private LatLng tempLatLng = new LatLng(37.5670135, 126.9783740) ; // 카메라 위치 지정 위한 좌표
    private List<LatLng> animal_hospital = new ArrayList<>(); // 동물 병원의 좌표열을 저장할 리스트
    private List<LatLng> animal_cafe = new ArrayList<>(); // 애견동반카페의 좌표열을 저장할 리스트
    private List<LatLng> animal_lost = new ArrayList<>(); // 분실 장소 좌표열을 저장할 리스트

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosp_cafe);

        mapView = findViewById(R.id.chmap_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync( this);

    }


    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        InfoWindow infoWindow = new InfoWindow(); // 마커 눌렀을 때 전화번호 나오게 끔
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });

        CameraPosition cameraPosition = new CameraPosition(tempLatLng, 18);
        naverMap.setCameraPosition(cameraPosition);

        Marker lostmarker1 = new Marker();
        lostmarker1.setPosition(new LatLng(37.5670135, 126.9783740));
        lostmarker1.setWidth(80);
        lostmarker1.setHeight(120);
        lostmarker1.setIcon(MarkerIcons.BLACK);
        lostmarker1.setIconTintColor(Color.RED); // 동반 가능 까페
        lostmarker1.setCaptionText("분실장소 1"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        lostmarker1.setTag("흰 강아지, 010-9704-8419");
        lostmarker1.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(lostmarker1);
            return true;
        });
        lostmarker1.setMap(naverMap);

        Marker lostmarker2 = new Marker();
        lostmarker2.setPosition(new LatLng(37.5659950, 126.9799105));
        lostmarker2.setWidth(80);
        lostmarker2.setHeight(120);
        lostmarker2.setIcon(MarkerIcons.BLACK);
        lostmarker2.setIconTintColor(Color.RED); // 동반 가능 까페
        lostmarker2.setCaptionText("분실장소 2"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        lostmarker2.setTag("얼룩 강아지, 010-9704-8419");
        lostmarker2.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(lostmarker2);
            return true;
        });
        lostmarker2.setMap(naverMap);

        Marker lostmarker3 = new Marker();
        lostmarker3.setPosition(new LatLng(37.5654269, 126.9758632));
        lostmarker3.setWidth(80);
        lostmarker3.setHeight(120);
        lostmarker3.setIcon(MarkerIcons.BLACK);
        lostmarker3.setIconTintColor(Color.RED); // 동반 가능 까페
        lostmarker3.setCaptionText("분실장소 3"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        lostmarker3.setTag("검은 강아지, 010-9704-8419");
        lostmarker3.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(lostmarker3);
            return true;
        });
        lostmarker3.setMap(naverMap);

        Marker lostmarker4 = new Marker();
        lostmarker4.setPosition(new LatLng(37.5682591, 126.9738985));
        lostmarker4.setWidth(80);
        lostmarker4.setHeight(120);
        lostmarker4.setIcon(MarkerIcons.BLACK);
        lostmarker4.setIconTintColor(Color.RED); // 동반 가능 까페
        lostmarker4.setCaptionText("분실장소 4"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        lostmarker4.setTag("노랑 강아지, 010-9704-8419");
        lostmarker4.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(lostmarker4);
            return true;
        });
        lostmarker4.setMap(naverMap);


        Marker cafemarker1 = new Marker();
        cafemarker1.setPosition(new LatLng(37.56787417, 126.98002365));
        cafemarker1.setWidth(80);
        cafemarker1.setHeight(120);
        cafemarker1.setIcon(MarkerIcons.BLACK);
        cafemarker1.setIconTintColor(Color.BLUE); // 동반 가능 까페
        cafemarker1.setCaptionText("애견동반카페 1"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        cafemarker1.setTag("010-9704-8419");
        cafemarker1.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(cafemarker1);
            return true;
        });
        cafemarker1.setMap(naverMap);

        Marker cafemarker2 = new Marker();
        cafemarker2.setPosition(new LatLng(37.57007705, 126.97798235));
        cafemarker2.setWidth(80);
        cafemarker2.setHeight(120);
        cafemarker2.setIcon(MarkerIcons.BLACK);
        cafemarker2.setIconTintColor(Color.BLUE); // 동반 가능 까페
        cafemarker2.setCaptionText("애견동반카페 2"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        cafemarker2.setTag(" 010-9704-8419");
        cafemarker2.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(cafemarker2);
            return true;
        });
        cafemarker2.setMap(naverMap);

        Marker cafemarker3 = new Marker();
        cafemarker3.setPosition(new LatLng(37.56551615, 126.9829572));
        cafemarker3.setWidth(80);
        cafemarker3.setHeight(120);
        cafemarker3.setIcon(MarkerIcons.BLACK);
        cafemarker3.setIconTintColor(Color.BLUE); // 동반 가능 까페
        cafemarker3.setCaptionText("애견동반카페 3"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        cafemarker3.setTag("010-9704-8419");
        cafemarker3.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(cafemarker3);
            return true;
        });
        cafemarker3.setMap(naverMap);


        Marker hosmarker1 = new Marker();
        hosmarker1.setPosition(new LatLng(37.56971986, 126.98563576));
        hosmarker1.setWidth(80);
        hosmarker1.setHeight(120);
        hosmarker1.setIcon(MarkerIcons.BLACK);
        hosmarker1.setIconTintColor(Color.YELLOW); // 동반 가능 까페
        hosmarker1.setCaptionText("동물병원 1"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        hosmarker1.setTag("010-9704-8419");
        hosmarker1.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(hosmarker1);
            return true;
        });
        hosmarker1.setMap(naverMap);

        Marker hosmarker2 = new Marker();
        hosmarker2.setPosition(new LatLng(37.56172668,
                126.97603802));
        hosmarker2.setWidth(80);
        hosmarker2.setHeight(120);
        hosmarker2.setIcon(MarkerIcons.BLACK);
        hosmarker2.setIconTintColor(Color.YELLOW); // 동반 가능 까페
        hosmarker2.setCaptionText("동물병원 2"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        hosmarker2.setTag("010-9704-8419");
        hosmarker2.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(hosmarker2);
            return true;
        });
        hosmarker2.setMap(naverMap);

        Marker hosmarker3 = new Marker();
        hosmarker3.setPosition(new LatLng(37.56172827,
                126.98217119));
        hosmarker3.setWidth(80);
        hosmarker3.setHeight(120);
        hosmarker3.setIcon(MarkerIcons.BLACK);
        hosmarker3.setIconTintColor(Color.YELLOW); // 동반 가능 까페
        hosmarker3.setCaptionText("동물병원 3"); // 캡션에 텍스트 지정 , 병원이나 카페 이름 지정하면 될
        hosmarker3.setTag("010-9704-8419");
        hosmarker3.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(hosmarker3);
            return true;
        });
        hosmarker3.setMap(naverMap);


    }

}
