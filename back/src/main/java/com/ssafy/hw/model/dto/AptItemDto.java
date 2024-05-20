package com.ssafy.hw.model.dto;

import lombok.Data;

@Data
public class AptItemDto {
	String aptCode;
	String aptName;
	String drmAddress;
	String bjdAddress;
	String bjdCode;
	int houseNum;
	int buildYear;
	int dongNum;
	int carNum;
	int cctvNum;
	double lng, lat;
	String aptType;
	String facility;
	String aptAnotherCode;
	int avgAmount;
	int avgDeposit;
	int avgMonthlyRent;
}
