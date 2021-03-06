package com.example.petdiary_2022;

import static com.example.petdiary_2022.MainActivity.user;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class GpsRecordActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    private BluetoothSPP bt;
    List<String> list;
    EditText et;
    Button stop;
    int i=0;
    String num;
    TextView test ;
    Path path = new Path();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_record);

            // 객체 생성 후 미리 선언한 변수에 넣음
            bt = new BluetoothSPP(this); //Initializing
            et = findViewById(R.id.et_san);
            stop = findViewById(R.id.btn_stop);


            if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가라면
                // 사용불가라고 토스트 띄워줌
                Toast.makeText(getApplicationContext()
                        , "Bluetooth is not available"
                        , Toast.LENGTH_SHORT).show();
                // 화면 종료
                finish();
            }


            // 데이터를 받았는지 감지하는 리스너
            bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {

                //데이터 수신되면
                public void onDataReceived(byte[] data, String message) {
                        num = Integer.toString(i);
//                    Toast.makeText(GpsRecordActivity.this, message, Toast.LENGTH_SHORT).show(); // 토스트로 데이터 띄움

                        path.name = et.getText().toString();
                        path.list.add(message);
                    i++;
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i = 0;
                    addGps(path); // firebase에 바로 저장
                    bt.disconnect();
                }
            });

            // 블루투스가 잘 연결이 되었는지 감지하는 리스너
            bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
                public void onDeviceConnected(String name, String address) {
                    Toast.makeText(getApplicationContext()
                            , "Connected to " + name + "\n" + address
                            , Toast.LENGTH_SHORT).show();
                }

                public void onDeviceDisconnected() { //연결해제
                    Toast.makeText(getApplicationContext()
                            , "Connection lost", Toast.LENGTH_SHORT).show();
                }

                public void onDeviceConnectionFailed() { //연결실패
                    Toast.makeText(getApplicationContext()
                            , "Unable to connect", Toast.LENGTH_SHORT).show();
                }
            });


            // 연결하는 기능 버튼 가져와서 이용하기
            Button btnConnect = findViewById(R.id.btnConnect); //연결시도
            // 버튼 클릭하면
            btnConnect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) { // 현재 버튼의 상태에 따라 연결이 되어있으면 끊고, 반대면 연결
                        bt.disconnect();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                    }
                }
            });
        }



        // 앱 중단시 (액티비티 나가거나, 특정 사유로 중단시)
        public void onDestroy() {
            super.onDestroy();
            bt.stopService(); //블루투스 중지
        }


        // 앱이 시작하면
        public void onStart() {
            super.onStart();
            if (!bt.isBluetoothEnabled()) { // 앱의 상태를 보고 블루투스 사용 가능하면
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 새로운 액티비티 띄워줌, 거기에 현재 가능한 블루투스 정보 intent로 넘겨
                startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
            } else {
                if (!bt.isServiceAvailable()) { // 블루투스 사용 불가
                    // setupService() 실행하도록
                    bt.setupService();
                    bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기끼리
                    // 셋팅 후 연결되면 setup()으로
                    setup();
                }
            }
        }



        // 블루투스 사용 - 데이터 전송
        public void setup() {
            Button btnSend = findViewById(R.id.btnSend); //데이터 전송
            btnSend.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    bt.send("Text", true);
                }
            });
        }


        // 새로운 액티비티 (현재 액티비티의 반환 액티비티?)
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            // 아까 응답의 코드에 따라 연결 가능한 디바이스와 연결 시도 후 ok 뜨면 데이터 전송
            if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) { // 연결시도
                if (resultCode == Activity.RESULT_OK) // 연결됨
                    bt.connect(data);
            } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) { // 연결 가능
                if (resultCode == Activity.RESULT_OK) { // 연결됨
                    bt.setupService();
                    bt.startService(BluetoothState.DEVICE_OTHER);
                    setup();
                } else { // 사용불가
                    Toast.makeText(getApplicationContext()
                            , "Bluetooth was not enabled."
                            , Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

    public void addGps(Path path) {
        databaseReference.child(user.id).child("GPS").child(path.name).setValue(path); // 산책명 > 번호 > GPS
    }

}

