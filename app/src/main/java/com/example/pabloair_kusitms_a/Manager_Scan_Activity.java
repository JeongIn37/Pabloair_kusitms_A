package com.example.pabloair_kusitms_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.io.IOException;
import java.security.acl.Permission;

public class Manager_Scan_Activity extends AppCompatActivity {

    private static final int SINGLE_PERMISSION = 1004; //권한 변수
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_scan);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        //카메라 권한 확인
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {
            //권한 요청 코드
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, SINGLE_PERMISSION);
        } else {
            //권한 O시 실행할 코드
            barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                    .setBarcodeFormats(Barcode.QR_CODE).build();

            textView = (TextView)findViewById(R.id.qrCode_text);
            surfaceView = findViewById(R.id.surfaceView);

            //카메라 - 바코드리더 연결
            cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                    .setRequestedPreviewSize(640,400).build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder surfaceholder) {
                    if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            !=PackageManager.PERMISSION_GRANTED) {
                        Log.d("surfaceCreated", "SUCCESS");
                        return;
                    }
                    try {
                        cameraSource.start(surfaceholder);
                        Log.d("CAMERA CONNECTING", "SUCCESS");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                    Log.d("Surface State", "Changed");
                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                    Log.d("Surface State", "Destroyed");
                }
            });

            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {
                }

                @Override
                public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                    if(qrcode.size() != 0) {
                        textView.post(new Runnable() {
                            @Override
                            //QR 코드 값을 TextView에 띄움
                            public void run() {
                                textView.setText(qrcode.valueAt(0).displayValue);
                            }
                        });
                    }
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "QR인식이 잘못되었습니다", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "주문번호: " + result.getContents() + "잠금 해제 완료", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

}