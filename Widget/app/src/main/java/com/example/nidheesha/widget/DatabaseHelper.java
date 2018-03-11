package com.example.nidheesha.widget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nidheesha on 11-Mar-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String TABLE_NAME = "grades";
    public static String DATABASE_NAME = "student";
    public static String COL_1 = "ID";
    public static String COL_2 = "NAME";
    public static String COL_3 = "GRADE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, GRADE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME );
        onCreate(sqLiteDatabase);
    }

    public boolean insert_data(String name,String grade){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,grade);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result==-1)return false;
        else return true;
    }
    public boolean update_data(String id ,String name, String grade){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,grade);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }
    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select* from "+TABLE_NAME,null);
        return cursor;
    }
    public  void delete(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,"id = ?",new String[]{id});
    }
}
