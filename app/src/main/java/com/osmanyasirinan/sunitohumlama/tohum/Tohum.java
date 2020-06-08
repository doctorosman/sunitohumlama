package com.osmanyasirinan.sunitohumlama.tohum;

public class Tohum {

    private String isim;
    private int miktar;

    public Tohum(String isim, int miktar) {
        this.isim = isim;
        this.miktar = miktar;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

}
