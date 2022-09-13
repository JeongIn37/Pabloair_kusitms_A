package com.example.pabloair_kusitms_a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {

    private Button buttonScan;
    private TextView SerializeNumber;

    //qr Code Scanner Object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonScan = (Button) findViewById(R.id.buttonScan);
        SerializeNumber = (TextView) findViewById(R.id.SerializeNumber);

        qrScan = new IntentIntegrator(this);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.setPrompt("Scanning..");

                qrScan.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(HomeActivity.this, "QR인식 실패", Toast.LENGTH_SHORT);
            } else {

                Toast.makeText(HomeActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject object = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    SerializeNumber.setText(result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

