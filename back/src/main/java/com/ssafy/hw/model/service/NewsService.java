package com.ssafy.hw.model.service;

import java.util.List;

import com.ssafy.hw.model.dto.NewsDto;

public interface NewsService {
	public List<NewsDto> searchNews(String keyword)  throws Exception ;
}
