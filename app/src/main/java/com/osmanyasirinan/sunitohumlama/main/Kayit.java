package com.osmanyasirinan.sunitohumlama.main;

public class Kayit {
    private String ay;
    private int ayNumber;
    private String sayi;

    public Kayit(String ay, int ayNumber,String sayi) {
        this.ay = ay;
        this.ayNumber = ayNumber;
        this.sayi = sayi;
    }

    public int getAyNumber() {
        return ayNumber;
    }

    public String getAy() {
        return ay;
    }

    public void setAy(String ay) {
        this.ay = ay;
    }

    public String getSayi() {
        return sayi;
    }

    public void setSayi(String sayi) {
        this.sayi = sayi;
    }
}
