package com.example.pabloair_kusitms_a;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Main_Order_List_Activity extends AppCompatActivity implements Serializable {

    ImageView c2, c3;
    View arrow2;

    private RecyclerView mRecyclerView;
    private Main_OrderListAdapter mRecyclerAdapter;
    public ArrayList<ListOrderItem> mOrderList;
    DBManager dbManager;

    //firestore 접근을 위한 객체
    private static FirebaseFirestore db;


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


        //커서를 이용하여 DB에 있는 정보 가져오기
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

    }

    private void GetListData() {
        mOrderList.clear();
        db.collection("OrderDetail").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) { //연결이 성공적일시
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        ListOrderItem item = documentSnapshot.toObject(ListOrderItem.class);
                        mOrderList.add(item);
                        Log.d("firestore connect is", "success");
                    }
                    } else {
                    Log.d("firestore connect is failed", String.valueOf(task.getException()));
                }
            }
        });
    }


}