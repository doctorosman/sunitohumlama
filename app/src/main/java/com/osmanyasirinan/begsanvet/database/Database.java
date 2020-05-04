package com.osmanyasirinan.begsanvet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hayvanlar";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLO_HAYVANLAR = "hayvanlar";
    private static final String ROW_ID = "id";
    private static final String ROW_SAHIP = "sahip";
    private static final String ROW_ESGAL = "esgal";
    private static final String ROW_TOHUM = "tohum";
    private static final String ROW_KOY = "koy";
    private static final String ROW_TARIH = "tarih";
    static Calendar c = Calendar.getInstance();
    private static final int YIL = c.get(Calendar.YEAR);

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLO_HAYVANLAR + "(" +
                ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROW_SAHIP + " TEXT NOT NULL, " +
                ROW_ESGAL + " TEXT NOT NULL, " +
                ROW_TOHUM + " TEXT NOT NULL, " +
                ROW_KOY + " TEXT NOT NULL, " +
                ROW_TARIH + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_HAYVANLAR);
        onCreate(db);
    }

    public void veriEkle(String sahip, String esgal, String tohum, String koy, String tarih){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_SAHIP, sahip);
            cv.put(ROW_ESGAL, esgal);
            cv.put(ROW_TOHUM, tohum);
            cv.put(ROW_KOY, koy);
            cv.put(ROW_TARIH, tarih);
            db.insert(TABLO_HAYVANLAR, null, cv);
        }catch (Exception e){

        }
        db.close();
    }

    public List<String> veriListele(){
        List<String> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] sutunlar = {ROW_ID, ROW_SAHIP, ROW_ESGAL, ROW_TOHUM, ROW_KOY, ROW_TARIH};
            Cursor cursor = db.query(TABLO_HAYVANLAR, sutunlar, null, null, null, null, ROW_ID + " DESC");
            while (cursor.moveToNext()){
                veriler.add(cursor.getString(1));
            }
        }catch (Exception e){

        }
        db.close();
        return veriler;
    }

    public List<Integer> idListele(){
        List<Integer> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] sutunlar = {ROW_ID, ROW_SAHIP, ROW_ESGAL, ROW_TOHUM, ROW_KOY, ROW_TARIH};
            Cursor cursor = db.query(TABLO_HAYVANLAR, sutunlar, null, null, null, null, ROW_ID + " DESC");
            while (cursor.moveToNext()){
                veriler.add(cursor.getInt(0));
            }
        }catch (Exception e){

        }
        db.close();
        return veriler;
    }

    public List<Integer> idListele(String s){
        List<Integer> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String[] sutunlar = {ROW_ID, ROW_SAHIP, ROW_ESGAL, ROW_TOHUM, ROW_KOY, ROW_TARIH};
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE " + ROW_SAHIP + " LIKE '%" + s + "%' ORDER BY id DESC", null, null);
            if (cursor.moveToFirst()){
                do {
                    veriler.add(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){}
        db.close();
        return veriler;
    }

    public List<String> veriListele(String s){
        List<String> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE " + ROW_SAHIP + " LIKE '%" + s + "%' ORDER BY id DESC", null, null);
            if (cursor.moveToFirst()){
                do {
                    veriler.add(cursor.getString(1));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){}
        db.close();
        return veriler;
    }

    public List<String> filterbyTarih(String date){

        List<String> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE " + ROW_TARIH + " LIKE '%" + date + "%' ORDER BY id DESC", null, null);
            if (cursor.moveToFirst()){
                do {
                    veriler.add(cursor.getString(1));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){}
        db.close();
        return veriler;
    }

    public Hayvan ara(int p) {
        Hayvan h = null;
        String q = "SELECT * FROM " + TABLO_HAYVANLAR + " WHERE id=" + p;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(q, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            h = new Hayvan(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }
        cursor.close();
        db.close();
        return h;
    }

    public void veriDuzenle(int id, String sahip, String esgal, String tohum, String koy, String tarih){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_SAHIP, sahip);
            cv.put(ROW_ESGAL, esgal);
            cv.put(ROW_TOHUM, tohum);
            cv.put(ROW_KOY, koy);
            cv.put(ROW_TARIH, tarih);
            String where = ROW_ID + " = '" + id + "'";
            db.update(TABLO_HAYVANLAR, cv, where, null);
        }catch (Exception e){

        }
        db.close();
    }

    public void veriSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String where = ROW_ID + " = " + id;
            db.delete(TABLO_HAYVANLAR, where, null);
        }catch (Exception e){

        }
        db.close();
    }

    public int getAyKayit(int a){
        String ay = this.putZeros(a);
        int sayi = 0;
        for (int i = 1; i <= 31; i++){
            String t = this.putZeros(i) + "." + ay + "." + YIL;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE tarih LIKE '%" + t + "%'", null);
            if (cursor.moveToFirst()){
                do {
                    sayi++;
                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return sayi;
    }

    public int getTarihKayit(String tarih){
        int sayi = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE tarih LIKE '%" + tarih + "%'", null);
        if (cursor.moveToFirst()){
            do {
                sayi++;
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sayi;
    }

    private String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }
}
