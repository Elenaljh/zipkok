package com.ssafy.hw.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hw.model.dto.NewsDto;
import com.ssafy.hw.model.service.NewsService;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*")
public class NewsController {
	private final NewsService newsService;
	public NewsController(NewsService newsService) {
		this.newsService = newsService;
	}
	
	@GetMapping
	public ResponseEntity<?> searchNews(@RequestParam("keyword") String keyword, @RequestParam(value="dong", required=false) String dong) {
		try {
			System.out.println("keyword="+keyword+", dong="+dong);
			List<NewsDto> list = newsService.searchNews(keyword, dong);
			return new ResponseEntity<List<NewsDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
    }
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
