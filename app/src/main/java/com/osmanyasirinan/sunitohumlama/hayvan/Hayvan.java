package com.osmanyasirinan.sunitohumlama.hayvan;

import com.osmanyasirinan.sunitohumlama.Utils;

import java.util.Calendar;

public class Hayvan {

    private String sahip, esgal, tohum, koy, tarih;
    private int gun, ay, yil;
    private int id;

    public Hayvan(){

    }

    public int getId() {
        return id;
    }

    public Hayvan(String sahip, String esgal, String tohum, String koy, String tarih) {
        this.sahip = sahip;
        this.esgal = esgal;
        this.tohum = tohum;
        this.koy = koy;
        this.tarih = tarih;
        this.gun = Integer.parseInt(new Utils().getFrom(tarih, 0));
        this.ay = Integer.parseInt(new Utils().getFrom(tarih, 1));
        this.yil = Integer.parseInt(new Utils().getFrom(tarih, 2));
    }

    public Hayvan(int id, String sahip, String esgal, String tohum, String koy, String tarih) {
        this.sahip = sahip;
        this.esgal = esgal;
        this.tohum = tohum;
        this.koy = koy;
        this.tarih = tarih;
        this.gun = Integer.parseInt(new Utils().getFrom(tarih, 0));
        this.ay = Integer.parseInt(new Utils().getFrom(tarih, 1));
        this.yil = Integer.parseInt(new Utils().getFrom(tarih, 2));
        this.id = id;
    }

    public void build() {
        this.gun = Integer.parseInt(new Utils().getFrom(tarih, 0));
        this.ay = Integer.parseInt(new Utils().getFrom(tarih, 1));
        this.yil = Integer.parseInt(new Utils().getFrom(tarih, 2));
    }

    public int getGun() {
        return gun;
    }

    public int getAy() {
        return ay;
    }

    public int getYil() {
        return yil;
    }

    public String getTarih() {
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

    public String getTahminiDogum() {
        Calendar c = Calendar.getInstance();
        c.set(getYil(), getAy(), getGun());
        c.add(Calendar.DAY_OF_MONTH, 280);
        return new Utils().putZeros(c.get(Calendar.DAY_OF_MONTH)) + "." + new Utils().putZeros(c.get(Calendar.MONTH)) + "." + c.get(Calendar.YEAR);
    }
}
