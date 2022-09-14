package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_Login_Activity extends AppCompatActivity {

    EditText id, pwd;
    Button login_btn;
    DBManager DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        DB = new DBManager(this);

        //기입 항목
        id = findViewById(R.id.login_id);
        pwd = findViewById(R.id.login_pwd);

        //로그인 버튼
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(v -> {

            String user_id = id.getText().toString();
            String user_pwd = pwd.getText().toString();

            if(TextUtils.isEmpty(user_id) || TextUtils.isEmpty(user_pwd)){
                Toast.makeText(User_Login_Activity.this, "아이디와 비밀번호를 모두 입력해야 합니다.", Toast.LENGTH_SHORT).show();
            } else {
                Boolean checkIdPwd = DB.checkIdPwd(user_id, user_pwd);
                if(checkIdPwd == true){
                    Toast.makeText(User_Login_Activity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(User_Login_Activity.this, "잘못된 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}