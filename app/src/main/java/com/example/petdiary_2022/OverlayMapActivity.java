package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.ArrowheadPathOverlay;
import com.naver.maps.map.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class OverlayMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    private MapView mapView;
    private static NaverMap naverMap;

    private LatLng tempLatLng ;
    private List<LatLng> firbase_latlng = new ArrayList<>(); // 산책한 경로의 좌표열을 저장할 리스트
    List<String> g_time = new ArrayList<>(); // 산책 시작, 종료 위한 것
    List<String> g_speed = new ArrayList<>(); // 산책 평균 속도를 구하기 위한 것

    Path path;
    String s_time; // 산책 시작 시간
    String e_time; // 산책 종료 시간
    String g_date; // 산책 날짜
    double speed; // 산책 속도

    TextView start_tv, end_tv, date_tv, speed_tv;

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

        start_tv = findViewById(R.id.p_start);
        end_tv = findViewById(R.id.p_end);
        date_tv = findViewById(R.id.p_date);
        speed_tv = findViewById(R.id.p_speed);


        LatLng temp_lat = new LatLng(0, 0); // 좌표 계산에 잠시 사용할 임시용
        for(int i=0;i<path.list.size();i++){
            String temp = path.list.get(i);
            String tmp[] = temp.split(",");
            if((tmp[3]==null)) {
                Toast.makeText(OverlayMapActivity.this, "좌표 값 불량", Toast.LENGTH_SHORT).show(); // 토스트로 데이터 띄움
                continue;
            }
            else {
                temp_lat = new LatLng(Latitude2Decimal(tmp[3], tmp[4]), Longitude2Decimal(tmp[5], tmp[6]));
                if(i==1){tempLatLng = temp_lat;}
                firbase_latlng.add(temp_lat);
                g_time.add(tmp[1]);
                g_speed.add(tmp[7]);
                g_date = tmp[9];
            }
        }



        // 시작 시간 설정
        s_time = g_time.get(0); // 첫 시간
        String timeTemp = s_time.substring(0,6);
        String hour[] = new String[4];
        hour[0] = timeTemp.substring(0,2); // 시
        int temp_sh = Integer.parseInt(hour[0])+9;
        if(temp_sh>24) temp_sh = temp_sh - 24;
        hour[0] = Integer.toString(temp_sh);
        hour[1] = timeTemp.substring(2,4); // 분
        String sttv = hour[0] +"시 " + hour[1] + "분";
        start_tv.setText(sttv);

        //날짜 설정, 윤년과 연도는 생각하지 않음, 우선 일과 월만 따짐
        String[] date = new String[3];
        date[0] = g_date.substring(0,2); // 월
        date[1] = g_date.substring(2,4); // 일
        int tmp_mon = Integer.parseInt(date[0]);
        int tmp_date = Integer.parseInt(date[1]);

        if(temp_sh>24){
            ++tmp_date;

            if(tmp_date==32){ // +9시간 했는 데 날짜가 32가 되면 달을 바꿔준다
                tmp_date =1;
                ++tmp_mon;
            }else if(tmp_date==31||(date[0].equals("04"))||(date[0].equals("06"))||(date[0].equals("09"))||(date[0].equals("11"))
            ){
                 tmp_mon = Integer.parseInt(date[0]);
                tmp_date =1;
                ++tmp_mon;
            }else if((tmp_date==29)){ //2월일 경우
               tmp_mon = Integer.parseInt(date[0]);
                tmp_date =1;
                ++tmp_mon;
            }

        }

        date[0] =Integer.toString(tmp_mon);
        date[1] = Integer.toString(tmp_date); // 만약 +9시간 했을 때 새벽이 되면 날짜를 +1일해줌

        date[2] = g_date.substring(g_date.length()-2,g_date.length()); // 년
        String dateTv = "20"+date[2]+"년 " + date[1]+"월 " + date[0] + "일";
        date_tv.setText(dateTv);

        // 끝나는 시간 설정
        e_time = g_time.get(g_time.size()-1);

        String timeTemp2 = e_time.substring(0,6);
        String hour2[] = new String[4];
        hour2[0] = timeTemp2.substring(0,2); // 시
        int temp_eh = Integer.parseInt(hour2[0])+9;
        if(temp_eh>24) temp_eh = temp_eh - 24;
        hour2[0] =Integer.toString(temp_eh); // 시
        hour2[1] = timeTemp2.substring(2,4); // 분
        String sttv2 = hour2[0] +"시 " + hour2[1] + "분";
        end_tv.setText(sttv2);

        //평균 속도
        double temp = 0;
        for(int i = 0; i < g_speed.size(); i++){
            double tmp_speed = Double.parseDouble(g_speed.get(i));
            temp += tmp_speed;
        }
        speed = temp/g_speed.size();
        String sptv = String.format("%.2f",1.8*speed); // 속도를 소수점 아래 3자리까지
        sptv = sptv + "km/h";
        speed_tv.setText(sptv);


    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;


        CameraPosition cameraPosition = new CameraPosition(tempLatLng, 18);
        naverMap.setCameraPosition(cameraPosition);

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
