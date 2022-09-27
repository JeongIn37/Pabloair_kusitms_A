package com.example.pabloair_kusitms_a;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main_Order_Activity extends AppCompatActivity {

    private static final String COLLECTION_NAME = "OrderDetail";
    private static FirebaseFirestore db;

    EditText nameEt, phoneEt, stationEt; //각 뷰 선언
    Button btn;
    String name, phoneNumber, station, serializedNum;
    Boolean insert;

    DBManager DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        btn = findViewById(R.id.order_submit_btn);
        nameEt = findViewById(R.id.order_username);
        phoneEt = findViewById(R.id.order_phone);
        stationEt = findViewById(R.id.order_station);

        DB = new DBManager(this);

        //버튼 클릭시 객체 생성
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                name = nameEt.getText().toString();
                phoneNumber = phoneEt.getText().toString();
                station = stationEt.getText().toString();

                if(name.length() == 0  | phoneNumber.length() == 0 | station.length() == 0) { //모든 값이 작성이 완료 되었고, 버튼을 눌렀을때
                    Toast.makeText(Main_Order_Activity.this, "값을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    Log.d("station", station);
                    Log.d("name", name);
                    Log.d("phoneNumber", phoneNumber);
                }
                else {
                    Log.d("station", station);
                    Log.d("name", name);
                    Log.d("phoneNumber", phoneNumber);
                    serializedNum = makeNewSerial();
                    Log.d("button", "enable");
                    Log.d("new Serial Num ", serializedNum); /*여기까지 문제 없음*/

                    //InnoDB에 값 넣기
                    insert = DB.insertOrder(name, serializedNum, station, Integer.parseInt(rndNum(8)+1), Integer.parseInt(rndNum(59)+1), 0);
                    // 주문
                    if (insert == true){
                        Toast.makeText(Main_Order_Activity.this, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Main_Order_Activity.this, "주문이 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }


    /* 주문 번호 작성 */

    private String makeNewSerial() {
        serializedNum = "A"+ getDate() + getStation() + rndAlpha() + rndNum(998);

        return serializedNum;
    }

    private String getDate() {
        //현재 시간 가져오기
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String codeDate = simpleDateFormat.format(date);

        return codeDate;

    }
    //스테이션별 알파벳 코드
    private String getStation() {
        String codeStation = "";
        if(station.equals("가평 남이섬 A1 스테이션")) {
            codeStation = "A";
        }
        else if(station.equals("가평 파인 포레스트")) {
            codeStation = "B";
        }
        else if(station.equals("가평군 농협 하나로마트 자라점")) {
            codeStation = "C";
        }
        else if(station.equals(null)) {
            Log.d("station input is ", "null");
        }

        return codeStation;
    }


    //랜점 알파벳 (코드 생성시 사용)
    private String rndAlpha() {
        Random rnd = new Random();
        String rndAlpha = String.valueOf((char) ((int) (rnd.nextInt(26))+65));

        return rndAlpha;
    }


    //랜덤 코드
    private String rndNum(int num) {
        Random rnd = new Random();
        int randNum = rnd.nextInt(num) + 1; //1~num까지 랜덤 숫자

       return String.valueOf(randNum);
    }

}







