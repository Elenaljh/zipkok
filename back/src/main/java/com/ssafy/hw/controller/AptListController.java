package com.ssafy.hw.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hw.model.service.AptListService;

@RestController
@RequestMapping("/apt/list")
@CrossOrigin(origins = "*")
public class AptListController {
	private final AptListService aptListService;
	public AptListController(AptListService aptListService) {
		this.aptListService = aptListService;
	}
	
	
	@
}
