package com.example.pabloair_kusitms_a;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Main_Order_List_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Main_OrderListAdapter mRecyclerAdapter;
    private ArrayList<ListOrderItem> mOrderItems;
    private ImageView QrCodeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_orderlist);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        mRecyclerAdapter = new Main_OrderListAdapter();

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mOrderItems = new ArrayList<>();
        for(int i=0; i<6; i++) {
            //짝수 - 이동 완료된 물품
            if(i%2 ==0)
                mOrderItems.add(new ListOrderItem("김유정", "A20220907AXC03", "가평 남이섬 A1 스테이션", 8.0,40));

            else
                //홀수 - 이동 중인 물품
                mOrderItems.add(new ListOrderItem("윤정인", "A202209198AZB04", "가평 파인 포레스트", 3.0, 20));
        }

        //리사이클러뷰 배치
        mRecyclerAdapter.setOrderList(mOrderItems);

        //클릭이벤트시 -> QrActivity
        mRecyclerAdapter.setOnItemClickListener(new Main_OrderListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                if(v == QrCodeIv) { //QrCode 클릭시 생성으로 넘어감
                    Intent intent = new Intent(getApplication(), Create_QRcodeActivity.class);
                    startActivity(intent);
                    Log.d("Activity", "OrderList_clickActivity");

                } else {
                    Intent intent = new Intent(getApplication(), OrderList_clickActivity.class);
                    startActivity(intent);
                    Log.d("Activity", "QrActivity");

                }
            }
        });
    }




}