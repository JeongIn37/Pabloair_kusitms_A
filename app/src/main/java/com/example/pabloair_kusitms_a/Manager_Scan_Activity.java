package com.example.pabloair_kusitms_a;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import org.w3c.dom.Document;

import java.io.IOException;
import java.security.acl.Permission;
import java.util.Locale;

public class Manager_Scan_Activity extends AppCompatActivity {

    private static final int SINGLE_PERMISSION = 1004; //권한 변수
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    DBManager dbManager;
    FirebaseFirestore db; //fireStore 연결

    int codeTest = 0;

    private String qrCodeContents =""; //qrCode 담는 값
    private Boolean document_OnGoing; //document - onGoing 담음


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_scan);

        //카메라 풀 스크린
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //화면이 켜진 상태로 유지
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //db매니저 생성
        dbManager = new DBManager(this);
        db = FirebaseFirestore.getInstance();


        //카메라 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //권한 요청 코드
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, SINGLE_PERMISSION);
        } else {
            //권한 O시 실행할 코드
            barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                    .setBarcodeFormats(Barcode.QR_CODE).build();


            //카메라 - 바코드리더 연결
            cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                    .setRequestedPreviewSize(640, 800)
                    .setFacing(CameraSource.CAMERA_FACING_BACK) //전면 카메라 사용
                    .setAutoFocusEnabled(true) //포커싱 허용
                    .build();

            surfaceView = findViewById(R.id.surfaceView);
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
                public void release() { }

                @Override
                public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> qrcode = detections.getDetectedItems();

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (qrCodeContents != null && qrcode.size() != 0) {
                                    qrCodeContents = qrcode.valueAt(0).displayValue;
                                    Log.d("Detection", qrCodeContents);
                                    verifySerial(qrCodeContents);
                                    //파이어베이스 확인 & toast message
                                    updateOnGoing();
                                }
                            } catch(Exception e) { }
                        }
                    }, 3000);

                    qrCodeContents ="";

                }
            });

            qrCodeContents ="";
        }
        setAnimation();
    }

    /* 함수 */

    //onGoing -> true (처리 완료되지 않은 주문건)
    private void onGoingToast() {
        if(qrCodeContents != "") {
            Toast toast = Toast.makeText(getApplicationContext(), "주문번호: " + qrCodeContents +"\n 스캔 완료 되었습니다", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            toast.show();
            Log.d("Order process finished", qrCodeContents);
        }
    }

    //onGoing -> false (처리 완료된 주문건)
    private void expiredToast() {
        if(qrCodeContents != "") {
            Toast toast = Toast.makeText(getApplicationContext(), "유효하지 않은 QR코드입니다", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            toast.show();
            Log.d("Expired Order", qrCodeContents);
        }

    }


    //Firestore와 검증코드
    private void verifySerial(String qrCodeContents) {
        DocumentReference dc = db.collection("OrderDetail").document(qrCodeContents);
        dc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException e) {
                if(e != null) {
                    Log.d("Listen Failed", String.valueOf(e));
                    return;
                }
                // IF the Document Exists
                if(value != null && value.exists()) {
                    Log.d("Current Data :", String.valueOf(value.getData()));
                    document_OnGoing = value.getBoolean("onGoing");
                    Log.d("document_onGoing", String.valueOf(document_OnGoing));

                    if(qrCodeContents != "" && document_OnGoing.equals(true)) { //알림 후 data 수정
                        onGoingToast();

                    }
                    else if (qrCodeContents !="" &&  document_OnGoing.equals(false)) {
                        expiredToast();
                    } else {
                        Log.d("Current data", "null");
                    }
                }

            }
        });
    }



    //레이어
    private void setAnimation() {
        final View line = (View) findViewById(R.id.line);
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                line.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        line.startAnimation(animation);
    }

    private void updateOnGoing() {
        DocumentReference dc = db.collection("OrderDetail").document(qrCodeContents);
        dc
                .update("onGoing", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override //update 성공시
                    public void onSuccess(Void unused) {
                        Log.d("DocumentSnapShot ", "successfully update");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override //update 실패시
                    public void onFailure(@NonNull Exception e) {
                        Log.d("error ", "updating document");
                    }
                });

    }

}