package com.ssafy.hw.model.dto;

public class PopulationBySexDto {
    private int zero;
    private int ten;
    private int twenty;
    private int thirty;
    private int forty;
    private int fifty;
    private int old;

    public PopulationBySexDto() {
    }

    public PopulationBySexDto(int zero, int ten, int twenty, int thirty, int forty, int fifty, int old) {
        this.zero = zero;
        this.ten = ten;
        this.twenty = twenty;
        this.thirty = thirty;
        this.forty = forty;
        this.fifty = fifty;
        this.old = old;
    }

    public int getZero() {
        return zero;
    }

    public void setZero(int zero) {
        this.zero = zero;
    }

    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    public int getTwenty() {
        return twenty;
    }

    public void setTwenty(int twenty) {
        this.twenty = twenty;
    }

    public int getThirty() {
        return thirty;
    }

    public void setThirty(int thirty) {
        this.thirty = thirty;
    }

    public int getForty() {
        return forty;
    }

    public void setForty(int forty) {
        this.forty = forty;
    }

    public int getFifty() {
        return fifty;
    }

    public void setFifty(int fifty) {
        this.fifty = fifty;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }
}
