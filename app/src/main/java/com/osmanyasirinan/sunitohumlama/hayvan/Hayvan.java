package com.osmanyasirinan.sunitohumlama.hayvan;

import com.osmanyasirinan.sunitohumlama.Utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Hayvan {

    private String sahip, esgal, tohum, koy;
    private Date tarih;
    private int id;

    public Hayvan(){

    }

    public int getId() {
        return id;
    }

    public Hayvan(String sahip, String esgal, String tohum, String koy, Date tarih) {
        this.sahip = sahip;
        this.esgal = esgal;
        this.tohum = tohum;
        this.koy = koy;
        this.tarih = tarih;
    }

    public Hayvan(int id, String sahip, String esgal, String tohum, String koy, Date tarih) {
        this.sahip = sahip;
        this.esgal = esgal;
        this.tohum = tohum;
        this.koy = koy;
        this.tarih = tarih;
        this.id = id;
    }

    public Date getTarih() {
        return tarih;
    }

    public String getSahip() {
        return sahip;
    }

    public String getEsgal() {
        return esgal;
    }

    public String getTohum() {
        return tohum;
    }

    public String getKoy() {
        return koy;
    }

    public String getTarihStr() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.tarih.getTime() * 1000L);
        return Utils.putZeros(c.get(Calendar.DAY_OF_MONTH)) + "." + Utils.putZeros(c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
    }

    public String getTahminiDogum() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.tarih.getTime() * 1000L);
        c.add(Calendar.DAY_OF_YEAR, 280);
        return Utils.putZeros(c.get(Calendar.DAY_OF_MONTH)) + "." + Utils.putZeros(c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
    }

}
