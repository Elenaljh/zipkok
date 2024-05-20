package com.ssafy.hw.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.hw.model.dto.AptItemDto;

public interface AptListDao {
	List<AptItemDto> getAptsByDong(String dong);
	List<AptItemDto> getAptsByName(Map<String, String> map);
	List<AptItemDto> getAptsByLatLngs(Map<String, Double> map);
	List<AptItemDto> getRecAptsByPfp(Map<String, Object> map);
	List<AptItemDto> getRecAptsByLoc(Map<String, Object> map);
}
