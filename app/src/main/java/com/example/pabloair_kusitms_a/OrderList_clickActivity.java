package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    private TextView ClickListOrderNum;
    String SerializedNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_click);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mRecyclerView = (RecyclerView) findViewById(R.id.clickList_rv);
        mRecyclerAdapter = new ClickItemAdapter();

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mClickList = new ArrayList<>();
        ClickListOrderNum = (TextView) findViewById(R.id.click_list_orderNumber);

        qrCode = (ImageView) findViewById(R.id.click_list_qrcode);

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
}