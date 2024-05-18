package com.ssafy.hw.model.dto;

public class PriceAvgDto {
    private int amount;
    private int deposit;
    private int monthlyRent;

    public PriceAvgDto() {
    }

    public PriceAvgDto(int amount, int deposit, int monthlyRent) {
        this.amount = amount;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(int monthlyRent) {
        this.monthlyRent = monthlyRent;
    }
}
