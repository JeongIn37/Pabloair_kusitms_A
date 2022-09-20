package com.example.pabloair_kusitms_a;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Order_Detail_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order_detail);


        ImageButton QrBtn = findViewById(R.id.qrCode_btn);
        View.OnClickListener BtnEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == QrBtn) {
                    Intent intent = new Intent(getApplication(), Main_Mypage_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "QR Code");
                }
            }
        };
        QrBtn.setOnClickListener(BtnEvent);

    }
}