package com.example.mj;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    static final String DATABASE_NAME = "mj.db";
    static final int DATABASE_VERSION = 1;

    //日別結果テーブル
    public static final String TABLE_NAME = "result";
    public static final String COL_RE_ID = "RE_ID";
    public static final String COL_DATE = "DATE";
    public static final String COL_USER1 = "USER1";
    public static final String COL_USER2 = "USER2";
    public static final String COL_USER3 = "USER3";
    public static final String COL_USER4 = "USER4";
    public static final String COL_USER5 = "USER5";
    public static final String COL_PLACE = "PLACE";
    public static final String COL_RATE = "RATE";
    public static final String COL_CHIP_RATE = "CHIP_RATE";
    public static final String COL_CHIP1 = "CHIP1";
    public static final String COL_CHIP2 = "CHIP2";
    public static final String COL_CHIP3 = "CHIP3";
    public static final String COL_CHIP4 = "CHIP4";
    public static final String COL_CHIP5 = "CHIP5";
    public static final String COL_TON = "TON";
    public static final String COL_ARIARI = "ARIARI";
    public static final String COL_RED = "RED";
    public static final String COL_OKA = "OKA";
    public static final String COL_UMA = "UMA";
    public static final String COL_NOTE = "NOTE";
    public static final String COL_COST = "COST";
    public static final String COL_LASTUPDATE = "lastupdate";

    //結果詳細テーブル
    public static final String TABLE_NAME_ROW = "result_row";
    public static final String COL_ID = "ID";
    public static final String COL_ROW_NUM = "ROW_NUM";
    public static final String COL_POINT1 = "POINT1";
    public static final String COL_POINT2 = "POINT2";
    public static final String COL_POINT3 = "POINT3";
    public static final String COL_POINT4 = "POINT4";
    public static final String COL_POINT5 = "POINT5";

    //ユーザテーブル
    public static final String TABLE_NAME_USER = "user";
    public static final String COL_USER_ID = "USER_ID";
    public static final String COL_USER_NAME = "USER_NAME";



    protected final Context context;
    protected DatabaseHelper dbHelper;
    protected SQLiteDatabase db;

    public DBAdapter(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(this.context);
    }


    //
    // SQLiteOpenHelper
    //

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + TABLE_NAME + " ("
                            + COL_RE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + COL_DATE + " TEXT NOT NULL,"
                            + COL_USER1 + " INTEGER ,"
                            + COL_USER2 + " INTEGER ,"
                            + COL_USER3 + " INTEGER ,"
                            + COL_USER4 + " INTEGER ,"
                            + COL_USER5 + " INTEGER ,"
                            + COL_PLACE + " TEXT ,"
                            + COL_RATE + " INTEGER ,"
                            + COL_CHIP_RATE + " INTEGER ,"
                            + COL_CHIP1 + " INTEGER ,"
                            + COL_CHIP2 + " INTEGER ,"
                            + COL_CHIP3 + " INTEGER ,"
                            + COL_CHIP4 + " INTEGER ,"
                            + COL_CHIP5 + " INTEGER ,"
                            + COL_TON + " INTEGER NOT NULL,"
                            + COL_ARIARI + " INTEGER NOT NULL,"
                            + COL_RED + " INTEGER NOT NULL,"
                            + COL_OKA + " INTEGER NOT NULL,"
                            + COL_UMA + " TEXT ,"
                            + COL_NOTE + " TEXT ,"
                            + COL_COST + " COST INTEGER,"
                            + COL_LASTUPDATE + " TEXT NOT NULL);");
            db.execSQL(
                    "CREATE TABLE " + TABLE_NAME_ROW + " ("
                            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + COL_RE_ID + " INTEGER NOT NULL ,"
                            + COL_ROW_NUM + " INTEGER NOT NULL ,"
                            + COL_POINT1 + " INTEGER  ,"
                            + COL_POINT2 + " INTEGER  ,"
                            + COL_POINT3 + " INTEGER  ,"
                            + COL_POINT4 + " INTEGER  ,"
                            + COL_POINT5 + " INTEGER  ,"
                            + COL_NOTE + " TEXT ,"
                            + COL_LASTUPDATE + " TEXT NOT NULL);");
            db.execSQL(
                    "CREATE TABLE " + TABLE_NAME_USER + " ("
                            + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + COL_USER_NAME + " TEXT NOT NULL ,"
                            + COL_NOTE + " TEXT ,"
                            + COL_LASTUPDATE + " TEXT NOT NULL);");

        }

        @Override
        public void onUpgrade(
                SQLiteDatabase db,
                int oldVersion,
                int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROW);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
            onCreate(db);
        }

    }

    //
    // Adapter Methods
    //

    public DBAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        dbHelper.close();
    }


    //
    // App Methods
    //


    public boolean deleteAllNotes(){
        return db.delete(TABLE_NAME, null, null) > 0;
    }

    public boolean deleteNote(String id){
        return db.delete(TABLE_NAME, "_id = ?", new String[]{id}) > 0;
    }

    public Cursor getAllNotes(){
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getDayNotes(String str){
        return db.query(TABLE_NAME, null, "DATE = ?", new String[]{ "" + str },  null, null, null);
    }




    public void updateUserName(int id, String name){
        Date dateNow = new Date ();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, id);
        values.put(COL_USER_NAME, name);
        values.put(COL_LASTUPDATE, dateNow.toLocaleString());
        db.replaceOrThrow(TABLE_NAME_USER, null, values);
    }

    public Cursor getYearList(){
        String str = "select DATES , count(DATES) COUNT from (select substr(date,1,4) DATES from result) GROUP BY DATES";
        return db.rawQuery(str,null);
    }


    //ユーザ毎の通算ポイント取得
    public Cursor getResultByUser(String userID, String year){
        String str = "select SUM(CASE" +
        " WHEN a.USER1 = "+ userID + " THEN POINT1 "+
        " WHEN a.USER2 = "+ userID + " THEN POINT2 "+
        " WHEN a.USER3 = "+ userID + " THEN POINT3 "+
        " WHEN a.USER4 = "+ userID + " THEN POINT4 "+
        " WHEN a.USER5 = "+ userID + " THEN POINT5 "+
        " ELSE 0 "+
        "END) POINT"+
        " from " +TABLE_NAME+" a INNER JOIN " + TABLE_NAME_ROW + " b on a.RE_ID = b.RE_ID WHERE a.DATE LIKE '"+ year + "%'";
        return db.rawQuery(str,null);
    }

    public int getCountByUser(String userID,String year){
        Cursor c;
        int count = 0;
        String str = "select SUM(CASE" +
                " WHEN USER1 = "+ userID + " THEN 1 "+
                " WHEN USER2 = "+ userID + " THEN 1 "+
                " WHEN USER3 = "+ userID + " THEN 1 "+
                " WHEN USER4 = "+ userID + " THEN 1 "+
                " WHEN USER5 = "+ userID + " THEN 1 "+
                " ELSE 0 "+
                "END) COUNT"+
                " from " +TABLE_NAME + " where DATE LIKE '" + year + "%'";
        c = db.rawQuery(str,null);
        if(c.moveToFirst()){
            do {
                count = c.getInt(c.getColumnIndex("COUNT"));
            }while(c.moveToNext());
        }
        return count;
    }

    public String getUserName(int id){
        Cursor c;
        String name = "";
        String str = "SELECT * from " + TABLE_NAME_USER + " WHERE USER_ID = " +id;
        c = db.rawQuery(str,null);
        if(c.moveToFirst()){
            do {
                name = c.getString(c.getColumnIndex(DBAdapter.COL_USER_NAME));
            }while(c.moveToNext());
        }
        return name;
    }

    public Cursor getUserList(){
        String str = "SELECT * from " + TABLE_NAME_USER;
        return db.rawQuery(str,null);
    }
    //ユーザ登録処理
    public  void saveUser(ArrayList<String> userArray){
        ArrayList<String> newUserList = new ArrayList<String>();
        String str = "SELECT "+ COL_USER_NAME + " FROM " + TABLE_NAME_USER;
        Cursor c = db.rawQuery(str,null);
        for (String name: userArray){
            newUserList.add(name);
            if(c.moveToFirst()){
                do {
                    if (name.equals(c.getString(c.getColumnIndex(DBAdapter.COL_USER_NAME)))){
                        newUserList.remove(newUserList.indexOf(name));
                    }
                }while(c.moveToNext());
            }

        }
        Date dateNow = new Date ();
        ContentValues values = new ContentValues();
        for(String name: newUserList){
            values.put(COL_USER_NAME,name);
            values.put(COL_LASTUPDATE, dateNow.toLocaleString());
            db.insertOrThrow(TABLE_NAME_USER,null,values);
        }

    }

    //ユーザID取得
    public Integer getUserID(String name){
        String str = "SELECT "+ COL_USER_ID + " FROM " + TABLE_NAME_USER + " WHERE " + COL_USER_NAME + " = '" + name + "'";
        Cursor c = db.rawQuery(str,null);
        int ID = 0;
        if(c.moveToFirst()){
            do {
                ID = c.getInt(c.getColumnIndex(DBAdapter.COL_USER_ID));
            }while(c.moveToNext());
        }
        return ID;
    }


    public int getLastRe_ID(){
        int ID = 0;
        String str = "SELECT "+ COL_RE_ID + " FROM " + TABLE_NAME + " ORDER BY " + COL_RE_ID + " DESC ";
        Cursor c = db.rawQuery(str,null);
        c.moveToFirst();
        if(c.getCount() == 0){
            ID = 0;
        }else{
            ID = c.getInt(c.getColumnIndex(DBAdapter.COL_RE_ID));
        }


        return ID;
    }

    public Cursor getResult(){
        String str = "SELECT * from " + TABLE_NAME;
        return db.rawQuery(str,null);
    }
    public Cursor getResultByID(int re_id){
        String str = "SELECT * from " + TABLE_NAME +" WHERE RE_ID = "+re_id ;
        return db.rawQuery(str,null);
    }
    public Cursor getResultRowByID(int re_id){
        String str = "SELECT * from " + TABLE_NAME_ROW +" WHERE RE_ID = "+re_id ;
        return db.rawQuery(str,null);
    }

    //結果登録処理
    public void saveResult(ArrayList<String> userArray,String date, String place, int rate, int chip_rate,ArrayList<Integer> chipArray,int ton, int ariari
    , int red, int oka ,String uma, int cost){
        //ユーザがいない場合は登録する。
        saveUser(userArray);

        //日別結果ＤＢに登録
        Date dateNow = new Date ();
        ContentValues values = new ContentValues();

        values.put(COL_DATE, date);
        int i = 1;
        for(String name: userArray){
            values.put("USER" + String.valueOf(i), getUserID(name));
            i++;
            //userArray.get(0);

        }
        if(!(place.equals(""))){ values.put(COL_PLACE, place); }
        values.put(COL_RATE, rate);
        if(!(String.valueOf(chip_rate).equals(""))){ values.put(COL_CHIP_RATE, chip_rate); }
        i = 1;
        for(int chip: chipArray){
            values.put("CHIP" + String.valueOf(i), chip);
            i++;
        }
        values.put(COL_TON, ton);
        values.put(COL_ARIARI, ariari);
        values.put(COL_RED, red);
        values.put(COL_OKA, oka);
        if(!(uma.equals(""))){ values.put(COL_UMA, uma); }
        values.put(COL_COST, cost);
        values.put(COL_LASTUPDATE, dateNow.toLocaleString());

        db.insertOrThrow(TABLE_NAME, null, values);

    }

    //結果詳細登録
    public  void saveRowResult(int id,int row_num,String point1,String point2, String point3, String point4, String point5){
        //結果詳細ＤＢに登録
        Date dateNow = new Date ();
        ContentValues values = new ContentValues();

        values.put(COL_RE_ID, id);
        values.put(COL_ROW_NUM, row_num);
        if(point1 != ""){ values.put(COL_POINT1, Integer.valueOf(point1)); }
        if(point2 != ""){ values.put(COL_POINT2, Integer.valueOf(point2)); }
        if(point3 != ""){ values.put(COL_POINT3, Integer.valueOf(point3)); }
        if(point4 != ""){ values.put(COL_POINT4, Integer.valueOf(point4)); }
        if(point5 != ""){ values.put(COL_POINT5, Integer.valueOf(point5)); }
        values.put(COL_LASTUPDATE, dateNow.toLocaleString());

        db.insertOrThrow(TABLE_NAME_ROW, null, values);

    }


}
