package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Button OrderBtn = findViewById(R.id.home_btn_order);
        Button OrderListBtn = findViewById(R.id.home_btn_orderList);
        Button SearchBtn = findViewById(R.id.home_btn_search);
        Button MyPageBtn = findViewById(R.id.home_btn_mypage);

        View.OnClickListener BtnEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == OrderBtn) {
                    Intent intent = new Intent(getApplication(), Main_Order_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "OrderActivity");
                } else if (v == OrderListBtn) {
                    Intent intent = new Intent(getApplication(), Main_Order_List_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "OrderListActivity");
                } else if (v == SearchBtn) {
                    Intent intent = new Intent(getApplication(), Main_Search_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "SearchActivity");
                } else if (v == MyPageBtn) {
                    Intent intent = new Intent(getApplication(), Main_Mypage_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "MyPageActivity");
                }

            }
        };
        OrderBtn.setOnClickListener(BtnEvent);
        OrderListBtn.setOnClickListener(BtnEvent);
        SearchBtn.setOnClickListener(BtnEvent);
        MyPageBtn.setOnClickListener(BtnEvent);

    }
}