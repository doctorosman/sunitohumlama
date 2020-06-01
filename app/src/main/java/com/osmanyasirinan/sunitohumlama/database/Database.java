package com.osmanyasirinan.sunitohumlama.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
    public int YIL = c.get(Calendar.YEAR);
    private Context context;

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public List<Integer> filterIdsbyTarih(String date){
        List<Integer> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE " + ROW_TARIH + " LIKE '%" + date + "%' ORDER BY id DESC", null, null);
            if (cursor.moveToFirst()){
                do {
                    veriler.add(cursor.getInt(0));
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
        String ay = new Utils().putZeros(a);
        int sayi = 0;
        for (int i = 1; i <= 31; i++){
            String t = new Utils().putZeros(i) + "." + ay + "." + YIL;
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

    public int getTotalCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
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

    public List<Hayvan> filtrele(String sahip, String esgal, String tohum, String koy, int ay, int yil) {
        List<Hayvan> list = new ArrayList<>();
        String sql = "SELECT id, sahip, esgal, tohum, koy, tarih FROM " + TABLO_HAYVANLAR;
        boolean putAnd = false;

        if (!sahip.equals("")){
            sql += " WHERE " + ROW_SAHIP + " LIKE '%" + sahip + "%'";
            putAnd = true;
        }

        if (!esgal.equals("")){
            if (putAnd)
                sql += " AND ";
            else
                sql += " WHERE ";
            sql += ROW_ESGAL + " LIKE '%" + esgal + "%'";
            putAnd = true;
        }

        if (!tohum.equals("")){
            if (putAnd)
                sql += " AND ";
            else
                sql += " WHERE ";
            sql += ROW_TOHUM + " LIKE '%" + tohum + "%'";
            putAnd = true;
        }

        if (!koy.equals("")){
            if (putAnd)
                sql += " AND ";
            else
                sql += " WHERE ";
            sql += ROW_KOY + " LIKE '%" + koy + "%'";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do {
                list.add(new Hayvan(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            }while (c.moveToNext());
        }
        c.close();
        db.close();

        if (ay != 0){
            List<Hayvan> newList = new ArrayList<>();

            for (Hayvan h : list) {
                if (h.getAy() == ay && h.getYil() == yil) {
                    newList.add(h);
                }
            }

            return newList;
        }else{
            return list;
        }
    }

    public void importrecords() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "import.tohumlama");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] hayvan = line.split("\\|");
                veriEkle(
                        hayvan[0],
                        hayvan[1],
                        hayvan[2],
                        hayvan[3],
                        hayvan[4]
                );
                line = reader.readLine();
            }
            Toast.makeText(context, "İçe aktarma başarılı.", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Hayvan> hayvanListele() {
        List<Hayvan> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR, null);
            if (cursor.moveToFirst()){
                do {
                    veriler.add(new Hayvan(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    ));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){

        }
        return veriler;
    }

    public void export(){
        int n = 0;
        String text = "";
        for (Hayvan h : hayvanListele()) {
            text += ((n != 0) ? "\n" : "") + h.getSahip() + "|" + h.getEsgal() + "|" + h.getTohum() + "|" + h.getKoy() + "|" + h.getTarih();
            n++;
        }

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "output.tohumlama");
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            BufferedWriter fos = new BufferedWriter(writer);
            fos.write(text);
            Toast.makeText(context, "Dışa aktarma başarılı", Toast.LENGTH_SHORT);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
