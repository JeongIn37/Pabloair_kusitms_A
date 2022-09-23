package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class OrderList_clickActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ClickItemAdapter mRecyclerAdapter;
    private ArrayList<ClickItem> mClickList;
    private ArrayList<ListOrderItem> mOrderList;

    private int OrderAmount;
    private ImageView qrCode;

    TextView ClickListOrderNum;
    TextView ClickListOrderName;
    TextView ClickListOrderStation;
    TextView ClickListAddress;
    TextView ClickListAddress1;

    String SerializedNum;
    int expire;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_click);

        //넘겨 받아야할 값: 앞 액티비티에서의 position 값
        Intent intent = getIntent(); //Serialized Num 받기
        position = intent.getIntExtra("post", 0);
        Log.d("position", String.valueOf(position)); //intent pos값 log

        //리스트 객체 intent로 받기
        mOrderList = intent.getParcelableArrayListExtra("OrderList");
        Log.i("List: ", "name: " + String.valueOf(mOrderList.get(position).name) + " OrderNum: "+ String.valueOf(mOrderList.get(position).serializedNumber) + " Station: " + String.valueOf(mOrderList.get(position).station));



        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        ClickListOrderNum = (TextView) findViewById(R.id.click_list_orderNumber);
        ClickListOrderName = (TextView) findViewById(R.id.click_list_orderName);
        ClickListOrderStation = (TextView) findViewById(R.id.click_list_station);
        ClickListAddress = (TextView) findViewById(R.id.click_list_address);
        ClickListAddress1 = (TextView) findViewById(R.id.click_list_address1);


        qrCode = (ImageView) findViewById(R.id.click_list_qrcode);

        mRecyclerView = (RecyclerView) findViewById(R.id.clickList_rv);
        mRecyclerAdapter = new ClickItemAdapter();

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mClickList = new ArrayList<>();

        //받은 position 으로 화면 구성
        setOrderList(position);


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

                    Intent intent = new Intent(getApplication(), Create_QRcodeActivity.class);

                    //주문 상세조회의 주문번호를 QrActivity에 넘기고 화면전환
                    SerializedNum = ClickListOrderNum.getText().toString();
                    intent.putExtra("SerializedNumber", SerializedNum);

                    //QR expire - Ongoing(0/1) 값으로 QR 토스트 전달
                    expire = mOrderList.get(position).onGoing;
                    intent.putExtra("expire", expire);

                    startActivity(intent);
                }
            }
        };

        qrCode.setOnClickListener(QrEvent);

    }


    void setOrderList(int pos) {
        ClickListOrderName.setText("주문자 성명 :" + mOrderList.get(position).name);
        ClickListOrderNum.setText("주문번호 " + mOrderList.get(position).serializedNumber);
        ClickListOrderStation.setText(mOrderList.get(position).station);

        if(mOrderList.get(position).station.equals("가평 남이섬 A1 스테이션"))  {
            ClickListAddress.setText("경기 가평군 가평읍 북한강변로 1037 (우) 12422");
            ClickListAddress1.setText("지번 | 가평읍 달천리 110-1");

        }
        else if(mOrderList.get(position).station.equals("가평 파인 포레스트")) {
            ClickListAddress.setText("경기 가평군 가평읍 당목가일길 654 (우) 12410");
            ClickListAddress1.setText("지번 | 가평읍 개곡리 147");

        }
        else if (mOrderList.get(position).station.equals("가평군 농협 하나로마트 자라점")) {
            ClickListAddress.setText("경기도 가평군 가평읍 호반로 2562(우) 12427");
            ClickListAddress1.setText("지번 | 가평읍 달천리 452-1");
        }

    }
}