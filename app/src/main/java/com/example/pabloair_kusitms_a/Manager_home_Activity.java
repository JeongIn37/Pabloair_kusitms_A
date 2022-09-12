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

public class Manager_home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        ImageButton ScanBtn = findViewById(R.id.manager_home_btn1);
        ImageButton ManageBtn = findViewById(R.id.manager_home_btn2);

        View.OnClickListener BtnEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == ScanBtn) {
                    Intent intent = new Intent(getApplication(), Manager_Scan_Activity.class);
                    startActivity(intent);
                    Log.d("Activity", "ScanActivity");

                } else if(v == ManageBtn) {
                    Intent intent = new Intent(getApplication(), DoorManageActivity.class);
                    startActivity(intent);
                    Log.d("Activity", "DoorManageActivity");
                }
            }
        };

        ScanBtn.setOnClickListener(BtnEvent);
        ManageBtn.setOnClickListener(BtnEvent);


    }
}