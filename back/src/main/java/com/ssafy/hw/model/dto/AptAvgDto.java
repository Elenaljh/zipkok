package com.ssafy.hw.model.dto;

import lombok.Data;

@Data
public class AptAvgDto {
	String dongCode;
	int avgAmount;
	int avgDeposit;
	int avgRent;
	String sidoName;
	String gugunName;
	String dongName;
	String fullName;
	double lat;
	double lng;
}
