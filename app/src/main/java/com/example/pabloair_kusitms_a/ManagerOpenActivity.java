package com.example.pabloair_kusitms_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class ManagerOpenActivity extends AppCompatActivity {

    Button openBtn;
    Button closeBtn;
    TextView backBtn;
    DBManager DBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_open);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        DBManager = new DBManager(this);
        openBtn = findViewById(R.id.manager_btn_open);
        closeBtn = findViewById(R.id.manager_btn_close);
        backBtn = findViewById(R.id.btnBack);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        View.OnClickListener layoutVisible = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == openBtn) {
                    openBtn.setSelected(true);
                    openBtn.setPressed(true);
                    closeBtn.setSelected(false);
                    closeBtn.setSelected(false);

                    Log.d("OpenBtn", "openBtn selected");

                } else if(v == closeBtn) {
                    openBtn.setSelected(false);
                    openBtn.setPressed(false);
                    closeBtn.setSelected(true);

                    Log.d("CloseBtn", "closeBtn selected");
                }
            }
        };

        openBtn.setOnClickListener(layoutVisible);
        closeBtn.setOnClickListener(layoutVisible);


        /* 관리자 Open 버튼 */
        openBtn.setOnClickListener(v -> {

            openBtn.setSelected(true);
            openBtn.setPressed(true);
            closeBtn.setSelected(false);
            closeBtn.setSelected(false);
            Log.d("OpenBtn", "openBtn selected");


            Map<String, Object> data = new HashMap<>();
            data.put("onGoing", true);
            db.collection("OrderDetail").document("A20220907AXC03").update(data);
            Log.d("=======success========", "open success");


            Toast.makeText(this, "Door opened", Toast.LENGTH_SHORT).show();
            //Log.d("-----------RP TEST-------------", db.collection("OrderDetail").document("A20220907AXC03").get().getResult().toString());
        });

        /* 관리자 Close 버튼 */
        closeBtn.setOnClickListener(v -> {

            openBtn.setSelected(false);
            openBtn.setPressed(false);
            closeBtn.setSelected(true);

            Log.d("CloseBtn", "closeBtn selected");

            Map<String, Object> data = new HashMap<>();
            data.put("onGoing", false);
            db.collection("OrderDetail").document("A20220907AXC03").update(data);
            Log.d("=======success========", "close success");
            Toast.makeText(this, "Door closed", Toast.LENGTH_SHORT).show();
        });

        /* 이전 버튼 */
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Manager_login_Activity.class);
            startActivity(intent);
        });
    }
}