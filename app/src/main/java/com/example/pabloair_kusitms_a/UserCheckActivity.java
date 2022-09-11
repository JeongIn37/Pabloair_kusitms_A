package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserCheckActivity extends AppCompatActivity {

    String UserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        ImageButton ManagerBtn = findViewById(R.id.user_check_manager_btn);
        ImageButton UserBtn = findViewById(R.id.user_check_user_btn);
        LinearLayout BottomLayout = findViewById(R.id.user_check_tv_layoutA);
        Button NextBtn = findViewById(R.id.user_check_next_btn);
        NextBtn.setVisibility(View.INVISIBLE);

        View.OnClickListener layoutVisible = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == UserBtn) {
                    UserBtn.setSelected(true);
                    UserBtn.setPressed(true);
                    ManagerBtn.setSelected(false);
                    ManagerBtn.setSelected(false);

                    BottomLayout.setVisibility(View.INVISIBLE);
                    NextBtn.setVisibility(View.VISIBLE);
                    UserType = "사용자";
                    Log.d("UserType", UserType);

                } else if(v == ManagerBtn) {
                    UserBtn.setSelected(false);
                    UserBtn.setPressed(false);
                    ManagerBtn.setSelected(true);
                    ManagerBtn.setSelected(true);

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

        TextView signupText = (TextView) findViewById(R.id.user_check_sign_in_tv);
        signupText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), User_Signup_Activity.class);
                startActivity(intent);
            }
        });

    }

}