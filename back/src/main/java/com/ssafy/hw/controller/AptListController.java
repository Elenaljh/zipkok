package com.ssafy.hw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ssafy.hw.model.dto.AptItemDto;
import com.ssafy.hw.model.service.AptListService;

@RestController
@RequestMapping("/apt/list")
@CrossOrigin(originPatterns = {"*"} ,allowCredentials = "true")
public class AptListController {
	private final AptListService aptListService;
	public AptListController(AptListService aptListService) {
		this.aptListService = aptListService;
	}
	
	
	@GetMapping("/dong")
	public ResponseEntity<?> getAptsByDong(@RequestParam("dong") String dong) {
		try {
			List<AptItemDto> list = aptListService.getAptsByDong(dong);
			return new ResponseEntity<List<AptItemDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	@GetMapping("/name")
	public ResponseEntity<?> getAptsByDong(@RequestParam("name") String name, @RequestParam(value="matchOption", required=false, defaultValue="exact") String matchOption) {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("name", name);
			map.put("matchOption", matchOption);
			List<AptItemDto> list = aptListService.getAptsByName(map);
			return new ResponseEntity<List<AptItemDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	@GetMapping("/range")
	public ResponseEntity<?> getAptsByLatLngs(@RequestParam Map<String, Double> map) {
		try { 
			List<AptItemDto> list = aptListService.getAptsByLatLngs(map);
			return new ResponseEntity<List<AptItemDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	@GetMapping("/rec")
	public ResponseEntity<?> getRecApts(@SessionAttribute(name="memberId", required = false) String memberId, @RequestParam("type") String type, @RequestParam(value="lng", required=false) Double lng, @RequestParam(value="lat", required=false) Double lat) {
		try {
			List<AptItemDto> list = new ArrayList<>();
			if(type.equals("login") && memberId != null) {
				System.out.println(memberId);
				list = aptListService.getRecAptsLogin(Integer.parseInt(memberId), lng, lat);
				System.out.println(list.size());
			} else {
				list = aptListService.getRecAptsDefault(lng, lat);
			}
			
			return new ResponseEntity<List<AptItemDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
