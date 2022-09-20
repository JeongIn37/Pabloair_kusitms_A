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
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        //카메라 풀 스크린
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //화면이 켜진 상태로 유지
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //카메라 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //권한 요청 코드
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, SINGLE_PERMISSION);
        } else {
            //권한 O시 실행할 코드
            barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                    .setBarcodeFormats(Barcode.QR_CODE).build();

//            textView = (TextView) findViewById(R.id.qrCode_text);
            surfaceView = findViewById(R.id.surfaceView);

            //카메라 - 바코드리더 연결
            cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                    .setRequestedPreviewSize(640, 800)
                    .setFacing(CameraSource.CAMERA_FACING_BACK) //전면 카메라 사용
//                    .setRequestedFps(29.8f) //프레임
                    .setAutoFocusEnabled(true) //포커싱 허용
                    .build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder surfaceholder) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
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
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (qrcode.size() != 0) {
                                String qrCodeContents = qrcode.valueAt(0).displayValue;
                                Log.d("Detection", qrCodeContents);
                                Toast toast = Toast.makeText(getApplicationContext(), "주문번호: " + qrcode.valueAt(0).displayValue +"\n 스캔 완료 되었습니다", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                                toast.show();
                            }
                        }
                    }, 0);

                }
            });
        }
    }

    //레이어
    private void setAnimation() {
        final View line = (View) findViewById(R.id.line);
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                line.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        line.startAnimation(animation);
    }

}