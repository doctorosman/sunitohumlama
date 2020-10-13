package com.osmanyasirinan.sunitohumlama;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.osmanyasirinan.sunitohumlama.hayvan.Hayvan;
import com.osmanyasirinan.sunitohumlama.tohum.Tohum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hayvanlar";
    private static final int DATABASE_VERSION = 1;

    private static final String ROW_ID = "id";

    private static final String TABLO_HAYVANLAR = "hayvanlar";
    private static final String ROW_SAHIP = "sahip";
    private static final String ROW_ESGAL = "esgal";
    private static final String ROW_TOHUM = "tohum";
    private static final String ROW_KOY = "koy";
    private static final String ROW_TARIH = "tarih";

    private static final String TABLO_TOHUMLAR = "tohumlar";
    private static final String ROW_ISIM = "isim";
    private static final String ROW_MIKTAR = "miktar";

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
                ROW_TARIH + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLO_TOHUMLAR + "(" +
                ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROW_ISIM + " TEXT NOT NULL, " +
                ROW_MIKTAR + " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_HAYVANLAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_TOHUMLAR);
        onCreate(db);
    }

    // HAYVANLAR TABLOSU

    public void hayvanEkle(String sahip, String esgal, String tohum, String koy, Date tarih){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_SAHIP, sahip);
            cv.put(ROW_ESGAL, esgal);
            cv.put(ROW_TOHUM, tohum);
            cv.put(ROW_KOY, koy);
            cv.put(ROW_TARIH, tarih.getTime());
            db.insert(TABLO_HAYVANLAR, null, cv);
        }catch (Exception ignored){}

        db.close();
    }

    public List<String> hayvanSahipListele(){
        List<String> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] sutunlar = {ROW_ID, ROW_SAHIP, ROW_ESGAL, ROW_TOHUM, ROW_KOY, ROW_TARIH};
            Cursor cursor = db.query(TABLO_HAYVANLAR, sutunlar, null, null, null, null, ROW_ID + " DESC");
            while (cursor.moveToNext()){
                veriler.add(cursor.getString(1));
            }
            cursor.close();
        }catch (Exception ignored){}

        db.close();
        return veriler;
    }

    public List<String> hayvanSahipListele(String s){
        List<String> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE " + ROW_SAHIP + " LIKE '%" + s + "%' ORDER BY id DESC", null, null);
            if (cursor.moveToFirst()){
                do {
                    veriler.add(cursor.getString(1));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception ignored){}
        db.close();
        return veriler;
    }

    public List<Integer> hayvanIdListele(){
        List<Integer> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] sutunlar = {ROW_ID, ROW_SAHIP, ROW_ESGAL, ROW_TOHUM, ROW_KOY, ROW_TARIH};
            Cursor cursor = db.query(TABLO_HAYVANLAR, sutunlar, null, null, null, null, ROW_ID + " DESC");
            while (cursor.moveToNext()){
                veriler.add(cursor.getInt(0));
            }
            cursor.close();
        }catch (Exception ignored){

        }
        db.close();
        return veriler;
    }

    public List<Integer> hayvanIdListele(String s){
        List<Integer> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_HAYVANLAR + " WHERE " + ROW_SAHIP + " LIKE '%" + s + "%' ORDER BY id DESC", null, null);
            if (cursor.moveToFirst()){
                do {
                    veriler.add(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception ignored){}

        db.close();
        return veriler;
    }

    public Hayvan getHayvan(int p) {
        Hayvan h = null;
        String q = "SELECT * FROM " + TABLO_HAYVANLAR + " WHERE id=" + p;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            h = new Hayvan(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), new Date(cursor.getLong(5)));
        }

        cursor.close();
        db.close();
        return h;
    }

    public void hayvanGuncelle(int id, String sahip, String esgal, String tohum, String koy, Date tarih){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_SAHIP, sahip);
            cv.put(ROW_ESGAL, esgal);
            cv.put(ROW_TOHUM, tohum);
            cv.put(ROW_KOY, koy);
            cv.put(ROW_TARIH, tarih.getTime());
            String where = ROW_ID + " = '" + id + "'";
            db.update(TABLO_HAYVANLAR, cv, where, null);
        }catch (Exception ignored){}

        db.close();
    }

    public void hayvanSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String where = ROW_ID + " = " + id;
            db.delete(TABLO_HAYVANLAR, where, null);
        }catch (Exception ignored){}

        db.close();
    }

    // TODO
    /*

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
    } */

    public List<Hayvan> hayvanFiltrele(String[] params) {
        List<Hayvan> list = new ArrayList<>();

        String sql = "SELECT id, sahip, esgal, tohum, koy, tarih FROM " + TABLO_HAYVANLAR;
        boolean putAnd = false;

        if (!params[0].equals("")){
            sql += " WHERE " + ROW_SAHIP + " LIKE '%" + params[0] + "%'";
            putAnd = true;
        }

        if (!params[1].equals("")){
            if (putAnd)
                sql += " AND ";
            else
                sql += " WHERE ";
            sql += ROW_ESGAL + " LIKE '%" + params[1] + "%'";
            putAnd = true;
        }

        if (!params[2].equals("")){
            if (putAnd)
                sql += " AND ";
            else
                sql += " WHERE ";
            sql += ROW_TOHUM + " LIKE '%" + params[2] + "%'";
            putAnd = true;
        }

        if (!params[3].equals("")){
            if (putAnd)
                sql += " AND ";
            else
                sql += " WHERE ";
            sql += ROW_KOY + " LIKE '%" + params[3] + "%'";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do {
                list.add(new Hayvan(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), new Date(c.getLong(5))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();

        return list;
    }

    public List<Hayvan> hayvanFiltrele(String[] params, Date baslangic) {
        List<Hayvan> list = hayvanFiltrele(params);
        List<Hayvan> result = new ArrayList<>();

        for (Hayvan h : list) {
            if (Utils.zeroizeTime(baslangic).getTime() == Utils.zeroizeTime(h.getTarih()).getTime())
                result.add(h);
        }

        return result;
    }

    public List<Hayvan> hayvanFiltrele(String[] params, Date baslangic, Date bitis) {
        List<Hayvan> list = hayvanFiltrele(params);
        List<Hayvan> result = new ArrayList<>();

        for (Hayvan h : list) {
            if (Utils.zeroizeTime(baslangic).getTime() <= h.getTarih().getTime() && Utils.zeroizeTime(bitis).getTime() >= h.getTarih().getTime())
                result.add(h);
        }

        return result;
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
                            new Date(cursor.getLong(5))
                    ));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception ignored){}

        return veriler;
    }

    public Set<Integer> getYears() {
        Set<Integer> years = new HashSet<>();

        for (Hayvan h : hayvanListele()) {
            years.add(Integer.parseInt(String.valueOf(h.getTarihStr().charAt(6)) + h.getTarihStr().charAt(7) + h.getTarihStr().charAt(8) + h.getTarihStr().charAt(9)));
        }

        return years;
    }

    // TODO
    /* public void importr(File f) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while (line != null) {
                String[] hayvan = line.split("\\|");
                hayvanEkle(
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
    } */

    // TODO
    /*public void exportr(){
        int n = 0;
        StringBuilder text = new StringBuilder();
        for (Hayvan h : hayvanListele()) {
            text.append((n != 0) ? "\n" : "").append(h.getSahip()).append("|").append(h.getEsgal()).append("|").append(h.getTohum()).append("|").append(h.getKoy()).append("|").append(h.getTarih());
            n++;
        }

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "output.tohumlama");
            FileWriter writer = new FileWriter(file);
            writer.write(text.toString());
            writer.close();
            Toast.makeText(context, "Kayıtlarınız downloads (indirilenler) klasörünüze aktarıldı.", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    } */

    // TOHUMLAR TABLOSU

    public void tohumEkle(String isim, int miktar){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ROW_ISIM, isim);
            values.put(ROW_MIKTAR, miktar);
            db.insert(TABLO_TOHUMLAR, null, values);
        }catch (Exception ignored){}
        db.close();
    }

    public Tohum tohumAra(int p) {
        Tohum tohum = null;
        String query = "SELECT * FROM " + TABLO_TOHUMLAR + " WHERE id=" + p;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();
        if (c.getCount() > 0) {
            tohum = new Tohum(c.getString(1), c.getInt(2));
        }
        c.close();
        db.close();
        return tohum;
    }

    public void tohumDuzenle(int id, String isim, int miktar) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ROW_ISIM, isim);
            values.put(ROW_MIKTAR, miktar);
            String where = ROW_ID + " = '" + id + "'";
            db.update(TABLO_TOHUMLAR, values, where, null);
        }catch (Exception ignored){}
        db.close();
    }

    public void tohumSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String where = ROW_ID + " = " + id;
            db.delete(TABLO_TOHUMLAR, where, null);
        }catch (Exception ignored){}
        db.close();
    }

    public List<Tohum> tohumListele() {
        List<Tohum> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_TOHUMLAR, null);
            if (cursor.moveToFirst()){
                do {
                    list.add(new Tohum(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2)
                    ));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception ignored){}

        db.close();
        return list;
    }

    public List<Integer> tohumIdListele() {
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_TOHUMLAR, null);
            if (cursor.moveToFirst()){
                do {
                    list.add(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception ignored){}

        db.close();
        return list;
    }

    public void tohumAzalt(int id) {
        tohumDuzenle(id, tohumAra(id).getIsim(), tohumAra(id).getMiktar() - 1);
    }

    public void tohumArttir(int id) {
        tohumDuzenle(id, tohumAra(id).getIsim(), tohumAra(id).getMiktar() + 1);
    }

    // TODO
    // tohumDisaAktar();
    // togumIceAktar();

}
