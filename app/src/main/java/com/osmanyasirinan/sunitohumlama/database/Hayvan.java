package com.osmanyasirinan.sunitohumlama.database;

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

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSahip() {
        return sahip;
    }

    public void setSahip(String sahip) {
        this.sahip = sahip;
    }

    public String getEsgal() {
        return esgal;
    }

    public void setEsgal(String esgal) {
        this.esgal = esgal;
    }

    public String getTohum() {
        return tohum;
    }

    public void setTohum(String tohum) {
        this.tohum = tohum;
    }

    public String getKoy() {
        return koy;
    }

    public void setKoy(String koy) {
        this.koy = koy;
    }

}
