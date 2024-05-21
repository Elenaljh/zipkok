package com.ssafy.hw.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.hw.model.dao.AptListDao;
import com.ssafy.hw.model.dto.AptItemDto;

@Service
public class AptListServiceImpl implements AptListService {
	private AptListDao aptListDao;
	public AptListServiceImpl(AptListDao aptListDao) {
		this.aptListDao = aptListDao;
	}
	
	
	@Override
	public List<AptItemDto> getAptsByDong(String dong) {
		return aptListDao.getAptsByDong(dong);
	}

	@Override
	public List<AptItemDto> getAptsByName(Map<String, String> map) {
		System.out.println(map);
		return aptListDao.getAptsByName(map);
	}

	@Override
	public List<AptItemDto> getAptsByLatLngs(Map<String, Double> map) {
		return aptListDao.getAptsByLatLngs(map);
	}
	

	@Override
	public List<AptItemDto> getRecAptsLogin(int memberId, double lng, double lat) {
		List<String> pfps = aptListDao.getPfps(memberId);
		Map<String, Object> paramMap = new HashMap<>();
		if(pfps.size() == 0) return getRecAptsDefault(lng, lat);
		if(pfps.size() >= 1) {
			paramMap.put("pfp1", pfps.get(0));
			paramMap.put("pfp1_cnt", 6);
			if(pfps.size() >= 2) {
				paramMap.put("pfp2",  pfps.get(1));
				paramMap.put("pfp1_cnt", 3);
				paramMap.put("pfp2_cnt", 3);
				if(pfps.size() >= 3) {
					paramMap.put("pfp3", pfps.get(1));
					paramMap.put("pfp1_cnt", 2);
					paramMap.put("pfp2_cnt", 2);
					paramMap.put("pfp3_cnt", 2);
				} 
			}
		}
		System.out.println(paramMap);
		return aptListDao.getRecAptsByPfp(paramMap);
	}


	@Override
	public List<AptItemDto> getRecAptsDefault(double lng, double lat) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lng", lng);
		paramMap.put("lat", lat);
		paramMap.put("loc_cnt", 6);
		return aptListDao.getRecAptsByLoc(paramMap);
	}

}
