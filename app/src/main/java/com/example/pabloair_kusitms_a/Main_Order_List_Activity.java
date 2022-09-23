package com.example.pabloair_kusitms_a;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Main_Order_List_Activity extends AppCompatActivity implements Serializable {

    private ImageView c1, c2, c3;
    private View arrow1, arrow2;

    private RecyclerView mRecyclerView;
    private Main_OrderListAdapter mRecyclerAdapter;
    public ArrayList<ListOrderItem> mOrderList;
    public ListOrderItem item;
    DBManager dbManager;


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
                    Intent intent = new Intent(getApplication(), OrderList_clickActivity.class);
                    intent.putParcelableArrayListExtra("OrderList", mOrderList); //리스트 전달
                    intent.putExtra("post", pos); //누른 아이템의 포지션 전달
                    startActivity(intent);
                    Log.d("post", String.valueOf(pos));
                    Log.d("Activity", "OrderList_clickActivity");

            }
        });

        // expire에 대한 이벤트 설정
        c1 = (ImageView) findViewById(R.id.list_ongoing_iv1);
        c2 = (ImageView) findViewById(R.id.list_ongoing_iv2);
        c3 = (ImageView) findViewById(R.id.list_ongoing_iv3);

        arrow1 = (View) findViewById(R.id.list_ongoing_arrow1);
        arrow2 = (View) findViewById(R.id.list_ongoing_arrow2);


    }

    void setExpireState() {
        for(int i = 0; i < mOrderList.size(); i++) {
            if(item.getOnGoing() == 0) {

            }

            else {

            }


        }
    }

}