package com.example.pabloair_kusitms_a;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Main_Order_List_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Main_OrderListAdapter mRecyclerAdapter;
    private ArrayList<ListOrderItem> mOrderList = new ArrayList<>();
    DBManager dbManager;
    String Listlength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_orderlist);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        mRecyclerAdapter = new Main_OrderListAdapter();

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrderList = new ArrayList<>();


        dbManager = new DBManager(this);
        SQLiteDatabase database = dbManager.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM OrderDetail", null);
        int recordCount = cursor.getCount();
        Log.d("recordCount", String.valueOf(recordCount));

        for(int i =0; i<recordCount; i++) {
            cursor.moveToNext();
            String name = cursor.getString(1);
            String SerializedNum = cursor.getString(2);
            String station = cursor.getString(3);
            int weight = cursor.getInt(4);
            int takeTime = cursor.getInt(5);
            int onGoing = cursor.getInt(6);

            Log.d("data", "success");
            mOrderList.add(new ListOrderItem(name,SerializedNum, station, weight,takeTime,onGoing));
        }
        cursor.close();
        Log.d("mOrderList: ", String.valueOf(mOrderList));
        mRecyclerAdapter.notifyDataSetChanged();



        //리사이클러뷰 배치
        mRecyclerAdapter.setOrderList(mOrderList);

        //클릭이벤트시 -> clickListActivity
        mRecyclerAdapter.setOnItemClickListener(new Main_OrderListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                    String posString = String.valueOf(pos);
                    Intent intent = new Intent(getApplication(), OrderList_clickActivity.class);
                    intent.putExtra("pos", posString);
                    startActivity(intent);
                    Log.d("Activity", "OrderList_clickActivity");

            }
        });


    }

}