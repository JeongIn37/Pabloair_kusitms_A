package com.example.pabloair_kusitms_a;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    public static final String DBNAME="PabloTeamA.db";
    public DBManager(Context context) {
        super(context, DBNAME, null, 1);
    }

    /* 새로운 테이블 생성 */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User(id TEXT PRIMARY KEY, pwd TEXT, name TEXT, phone INTEGER);");
        db.execSQL("CREATE TABLE OrderList(id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT , serialNum TEXT, perform INTEGER);");
        db.execSQL("INSERT INTO User VALUES ('pabloair', '1234567', '파블로항공', '010-1234-5678')");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220907AXC03', 0)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220911B12DX', 0)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220912CK782', 1)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A2022091208FVX', 1)");
        db.execSQL("INSERT INTO OrderList (userId, serialNum, perform) VALUES ('pabloair', 'A20220911AXC03', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS OrderList");
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


//    public void insert(String _query) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL(_query);
//        db.close();
//    }
//
//
//    public void update(String _query) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL(_query);
//        db.close();
//    }
//
//    public void delete(String _query) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL(_query);
//        db.close();
//    }

}