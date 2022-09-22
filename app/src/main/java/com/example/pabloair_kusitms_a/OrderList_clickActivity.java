package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class OrderList_clickActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ClickItemAdapter mRecyclerAdapter;
    private ArrayList<ClickItem> mClickList;

    private int OrderAmount;
    private ImageView qrCode;

    TextView ClickListOrderNum;
    TextView ClickListOrderName;
    TextView ClickListOrderStation;

    String SerializedNum, Num;
    String name, station;
    int position;

    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_click);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        //넘겨 받아야할 값: 앞 액티비티에서의 position 값

        Intent intent = getIntent(); //Serialized Num 받기
        position = Integer.parseInt(intent.getStringExtra("pos"));
        Log.d("position", String.valueOf(position));

        dbManager = new DBManager(this);
        SQLiteDatabase db = dbManager.getReadableDatabase();

        ClickListOrderNum = (TextView) findViewById(R.id.click_list_orderNumber);
        ClickListOrderName = (TextView) findViewById(R.id.click_list_orderName);
        ClickListOrderStation = (TextView) findViewById(R.id.click_list_station);

        Cursor cursor = db.rawQuery("SELECT name, serializedNumber, station FROM OrderDetail", null);
        Log.d("Success", "rawQuery");
        cursor.moveToPosition(position);
        name = cursor.getString(1);
        Num = cursor.getString(2);
        station = cursor.getString(3);
        Log.d("Success", "move");
        ClickListOrderName.setText("주문자 성명: " + name);
        Log.d("Success", "1");
        ClickListOrderNum.setText("주문번호 " + Num);
        Log.d("Success", "2");
        ClickListOrderStation.setText(station);
        Log.d("Success", "3");

        qrCode = (ImageView) findViewById(R.id.user_qrCode_background);

        mRecyclerView = (RecyclerView) findViewById(R.id.clickList_rv);
        mRecyclerAdapter = new ClickItemAdapter();

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mClickList = new ArrayList<>();

        qrCode = (ImageView) findViewById(R.id.click_list_qrcode);
        ClickListOrderNum = (TextView) findViewById(R.id.click_list_orderNumber);
        ClickListOrderName = (TextView) findViewById(R.id.click_list_orderName);
        ClickListOrderStation = (TextView) findViewById(R.id.click_list_station);


        OrderAmount = 7;
        //더미데이터 삽입
        for(int i =0; i<OrderAmount; i++) {
            if(i == 0) {
                mClickList.add(new ClickItem("청보 깐마늘 100g", 1, 2390, R.drawable.order_galic));

            } else if(i%2==0) {
                mClickList.add(new ClickItem("성게알 감태초밥", 2, 32900, R.drawable.product_photo3));

            } else {
                mClickList.add(new ClickItem("백골뱅이탕&알품은한치&아귀간", 1, 49900, R.drawable.product_photo4));

            }
            mRecyclerAdapter.setClickList(mClickList);
        }


        View.OnClickListener QrEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == qrCode) {
                    //주문 상세조회의 주문번호를 QrActivity에 넘기고 화면전환
                    SerializedNum = ClickListOrderNum.getText().toString();
                    Intent intent = new Intent(getApplication(), Create_QRcodeActivity.class);
                    intent.putExtra("SerializedNumber", SerializedNum);
                    startActivity(intent);
                }
            }
        };

        qrCode.setOnClickListener(QrEvent);

    }

    void SetOrderList(int pos) {


    }
}