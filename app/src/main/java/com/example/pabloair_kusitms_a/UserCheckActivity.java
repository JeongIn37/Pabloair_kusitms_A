package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class UserCheckActivity extends AppCompatActivity {

    String UserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);


        ImageButton ManagerBtn = findViewById(R.id.user_check_manager_btn);
        ImageButton UserBtn = findViewById(R.id.user_check_user_btn);
        LinearLayout BottomLayout = findViewById(R.id.user_check_tv_layoutA);
        Button NextBtn = findViewById(R.id.user_check_next_btn);
        NextBtn.setVisibility(View.INVISIBLE);

        View.OnClickListener layoutVisible = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == UserBtn) {
                    BottomLayout.setVisibility(View.INVISIBLE);
                    NextBtn.setVisibility(View.VISIBLE);
                    UserType = "사용자";
                    Log.d("UserType", UserType);

                } else if(v == ManagerBtn) {
                    BottomLayout.setVisibility(View.INVISIBLE);
                    NextBtn.setVisibility(View.VISIBLE);
                    UserType = "관리자";
                    Log.d("UserType", UserType);
                }
            }
        };

        UserBtn.setOnClickListener(layoutVisible);
        ManagerBtn.setOnClickListener(layoutVisible);



        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserType.equals("사용자")) {

                    startActivity(new Intent(getApplication(), User_Login_Activity.class));

                } else if (UserType.equals("관리자")) {

                    startActivity(new Intent(getApplication(), Manager_login_Activity.class));
                }

            }
        });

    }

}