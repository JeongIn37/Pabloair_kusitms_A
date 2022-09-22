package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class Create_QRcodeActivity extends AppCompatActivity {

    ImageView qrCode;
    String SerializedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qrcode);


        //넘겨 받아야할 값: DB에서의 주문번호 값
        Intent intent = getIntent(); //Serialized Num 받기
        SerializedNumber = intent.getStringExtra("SerializedNumber");
        qrCode = (ImageView) findViewById(R.id.user_qrCode_background);

        //하단바 적용
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(SerializedNumber, BarcodeFormat.QR_CODE, 300,300);
            Log.d("QRCode 생성 주문번호", SerializedNumber);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

            //xml image에 부착
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
            if(qrCode == null) {
                Log.d("Error", "Null of Exception");
            } else {
                Log.d("부착성공", "Success");
            }


        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


}
