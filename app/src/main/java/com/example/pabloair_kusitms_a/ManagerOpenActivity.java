package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerOpenActivity extends AppCompatActivity {

    ImageButton openBtn;
    ImageButton closeBtn;
    TextView backBtn;
    DBManager DBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_open);

        DBManager = new DBManager(this);
        openBtn = findViewById(R.id.manager_btn_open);
        closeBtn = findViewById(R.id.manager_btn_close);
        backBtn = findViewById(R.id.btnBack);

        /* 관리자 Open 버튼 */
        openBtn.setOnClickListener(v -> {

        });

        /* 관리자 Close 버튼 */
        closeBtn.setOnClickListener(v -> {

        });

        /* 이전 버튼 */
        backBtn.setOnClickListener(v -> {

        });
    }
}