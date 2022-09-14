package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Manager_home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        ImageButton ScanBtn = findViewById(R.id.manager_home_btn1);
        ImageButton ManageBtn = findViewById(R.id.manager_home_btn2);
        Button QrLayoutBtn = findViewById(R.id.qrCode_layout_btn);

        ImageView qrCode = (ImageView) findViewById(R.id.qrCode);
        String SerialNumber = "A20220907AXC03";

        View.OnClickListener BtnEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == ScanBtn) {
                    Intent intent = new Intent(getApplication(), Manager_Scan_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "ScanActivity");

                }
                else if(v == ManageBtn) {
                    Intent intent = new Intent(getApplication(), Manager_login_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "DoorManageActivity");
                }
                else if(v == QrLayoutBtn) {

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        //QR Code 생성 (content -원하는 내용, format- 바코드 포맷형식, 가로, 세로)
                        BitMatrix bitMatrix = multiFormatWriter.encode(SerialNumber, BarcodeFormat.QR_CODE, 200, 200);
                        Log.d("QRCODE 생성", SerialNumber);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrCode.setImageBitmap(bitmap);
                        if(qrCode == null) {
                            Log.d("ERROR", "NUllPointerException");
                        } else {
                            Log.d("부착성공", "Success");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        ScanBtn.setOnClickListener(BtnEvent);
        ManageBtn.setOnClickListener(BtnEvent);
        QrLayoutBtn.setOnClickListener(BtnEvent);


    }
}