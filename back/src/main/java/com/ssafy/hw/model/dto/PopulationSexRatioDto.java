package com.ssafy.hw.model.dto;

public class PopulationSexRatioDto {
    public double male;
    public double female;

    public PopulationSexRatioDto() {
    }

    public PopulationSexRatioDto(double male, double female) {
        this.male = male;
        this.female = female;
    }

    public double getMale() {
        return male;
    }

    public void setMale(double male) {
        this.male = male;
    }

    public double getFemale() {
        return female;
    }

    public void setFemale(double female) {
        this.female = female;
    }
}
