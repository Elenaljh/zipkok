package com.ssafy.hw.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hw.model.dto.AptItemDto;

public interface AptListService {
	List<AptItemDto> getAptsByDong(String dong);
	List<AptItemDto> getAptsByName(Map<String, String> map);
	List<AptItemDto> getAptsByLatLngs(Map<String, Double> map);
	List<AptItemDto> getRecAptsLogin(int memberId, double lng, double lat);
	List<AptItemDto> getRecAptsDefault(double lng, double lat);
}
