package com.ssafy.hw.model.dto;

public class AptTransaction {
    private long transactionId;
    private long amount; //매매가
    private long deposit; //보증금(또는 전세금)
    private int monthlyRent; //월세
    private String type;
    private String aptName;
    private double area;
    private int dong;
    private int floor;
    private int year;
    private int month;
    private int day;
    private String bjdCode;
    private String streetCode;
    private String aptCode;

    public AptTransaction() {
    }

    public AptTransaction(long transactionId, long amount, long deposit, int monthlyRent, String type, String aptName, double area, int dong, int floor, int year, int month, int day, String bjdCode, String streetCode, String aptCode) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.type = type;
        this.aptName = aptName;
        this.area = area;
        this.dong = dong;
        this.floor = floor;
        this.year = year;
        this.month = month;
        this.day = day;
        this.bjdCode = bjdCode;
        this.streetCode = streetCode;
        this.aptCode = aptCode;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getDeposit() {
        return deposit;
    }

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public int getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(int monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getDong() {
        return dong;
    }

    public void setDong(int dong) {
        this.dong = dong;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getBjdCode() {
        return bjdCode;
    }

    public void setBjdCode(String bjdCode) {
        this.bjdCode = bjdCode;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getAptCode() {
        return aptCode;
    }

    public void setAptCode(String aptCode) {
        this.aptCode = aptCode;
    }
}
