package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Manager_login_Activity extends AppCompatActivity {

    EditText adminIdInput;
    Button loginBtn;
    DBManager DBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        DBManager = new DBManager(this);
        adminIdInput = findViewById(R.id.login_id);
        loginBtn = findViewById(R.id.manager_login_btn);

        /* 관리자 로그인 버튼 */
        loginBtn.setOnClickListener(v -> {

            String adminId = adminIdInput.getText().toString();
            if(TextUtils.isEmpty(adminId)){
                Toast.makeText(Manager_login_Activity.this, "관리자 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                Boolean checkAdmin = DBManager.checkAdmin(adminId);
                if (checkAdmin == true) {
                    Toast.makeText(Manager_login_Activity.this, "관리자 인증에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, DoorManageActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(Manager_login_Activity.this, "잘못된 관리자 번호입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}