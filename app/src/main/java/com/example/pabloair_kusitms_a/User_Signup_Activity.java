package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class User_Signup_Activity extends AppCompatActivity {

    ImageView back;
    EditText id, pwd, name, phone;
    Button signup_btn;
    DBManager DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        //뒤로 가기 버튼
        back = findViewById(R.id.ic_backward);
        back.setOnClickListener(v -> onBackPressed() );

        //기입 항목
        id = findViewById(R.id.signup_id);
        pwd = findViewById(R.id.signup_pwd);
        name = findViewById(R.id.signup_name);
        phone = findViewById(R.id.signup_phone);

        DB = new DBManager(this);

        //회원가입 완료 버튼
        signup_btn = findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(v -> {

            String user_id = id.getText().toString();
            String user_pwd = pwd.getText().toString();
            String user_name = name.getText().toString();
            String user_phone = phone.getText().toString();

            // 회원가입 공란, 아이디 중복 메시지 처리
            if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(user_pwd) || TextUtils.isEmpty(user_name) || TextUtils.isEmpty(user_phone)){
                Toast.makeText(User_Signup_Activity.this, "모든 값을 입력해야 합니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Boolean checkUser = DB.checkUserId(user_id);
                // id가 존재하지 않으면 회원가입 진행
                if(checkUser == false){
                    Boolean insert = DB.insertData(user_id, user_pwd, user_name, user_phone);
                    // 회원가입
                    if (insert == true){
                        Toast.makeText(User_Signup_Activity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, User_Login_Activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(User_Signup_Activity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(User_Signup_Activity.this, "이미 등록된 아이디입니다.", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
}