package com.example.pabloair_kusitms_a;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class DBManager extends SQLiteOpenHelper {

    public static final String DBNAME="PabloTeamA.db";
    public DBManager(Context context) {
        super(context, DBNAME, null, 1);
    }

    /* 새로운 테이블 생성 */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* User Table */
        db.execSQL("CREATE TABLE User(id TEXT PRIMARY KEY, pwd TEXT, name TEXT, phone INTEGER);");
        db.execSQL("INSERT INTO User VALUES ('pabloair', '1234567', '파블로항공', '010-1234-5678')");

        /* OrderList Table */
        db.execSQL("CREATE TABLE OrderList(id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT , serialNum TEXT, perform INTEGER);");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220907AXC03', 0)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220911B12DX', 0)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220912CK782', 1)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A2022091208FVX', 1)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220911AXC03', 2)");

        /* Administrator Table */
        db.execSQL("CREATE TABLE Admin(adminId TEXT PRIMARY KEY);");
        db.execSQL("INSERT INTO Admin VALUES ('yujeong00');");
        db.execSQL("INSERT INTO Admin VALUES ('jeongin99');");
        db.execSQL("INSERT INTO Admin VALUES ('minseo00');");

        /*OrderDetail Table*/
        db.execSQL("CREATE TABLE IF NOT EXISTS OrderDetail(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , serializedNumber TEXT, station TEXT, weight INTEGER, takeTime INTEGER, onGoing INTEGER);");
        db.execSQL("INSERT INTO OrderDetail (name, serializedNumber, station, weight, takeTime, onGoing) VALUES ('김유정', 'A20220907AXC03', '가평 남이섬 A1 스테이션', 8, 40, 1)");
        db.execSQL("INSERT INTO OrderDetail (name, serializedNumber, station, weight, takeTime, onGoing) VALUES ('윤정인', 'A20220911B12DX', '가평 파인 포레스트', 3, 20, 0)");
        db.execSQL("INSERT INTO OrderDetail (name, serializedNumber, station, weight, takeTime, onGoing) VALUES ('신민서', 'A20220912CK782', '가평군 농협 하나로마트 자라점', 5, 40, 0)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS OrderList");
        db.execSQL("DROP TABLE IF EXISTS OrderDetail");

    }

    //오더 값 집어넣기
    public Boolean insertOrder(String name, String serializedNumber, String station, int weight, int takeTime, int onGoing){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

//        values.put("id", id);
        values.put("name",name);
        values.put("serializedNumber", serializedNumber);
        values.put("station", station);
        values.put("weight", weight);
        values.put("takeTime", takeTime);
        values.put("onGoing", onGoing);

        long result = db.insert("OrderDetail", null, values);
        if (result == -1 ) {
            Log.d("insertOrder: ", "null");
            return false;
        }
        else {
            Log.d("insertOrder: ", "success");
            return true;
        }

    }



    public Boolean insertData(String id, String pwd, String name, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("pwd", pwd);
        values.put("name", name);
        values.put("phone", phone);

        long result = db.insert("User", null, values);
        if (result == -1 ) {
            return false;
        }
        else {
            return true;
        }

    }

    // 회원가입 아이디 중복 확인
    public Boolean checkUserId(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from User where id=?", new String[] {id});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    // 로그인(ID, PW 확인)
    public Boolean checkIdPwd(String id, String pwd){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from User where id=? and pwd=?", new String[] {id, pwd});
        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkAdmin(String adminId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Admin where adminId=?", new String[] {adminId});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    //QR스캔 확인(주문번호 - QR)
    public Boolean checkQR(String serializedNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from OrderDetail where serializedNumber=?", new String[] {serializedNumber});

        if(cursor.getCount() >0) {
            return true;
        }
        else {
            return false;
        }
    }

    //QRExpire 확인(주문번호 - Ongoing)
    public Boolean checkExpire(String serializedNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select onGoing from OrderDetail where serializedNumber=? and onGoing=0", new String[] {serializedNumber});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //리사이클러뷰 커서 전달
    public Cursor rawQuery(String SQL) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("executeQuery", "called");
        Cursor c1 = null;

        try {
            c1 = db.rawQuery(SQL,null);
            String cursorPos = String.valueOf(c1.getCount());
            Log.d("cursor count", cursorPos);
        } catch(Exception e) {
            Log.d("ERROR", "Exception in executeQuery");


        }
        return c1;
    }

}