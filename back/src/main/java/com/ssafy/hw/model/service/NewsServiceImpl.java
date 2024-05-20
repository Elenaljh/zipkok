package com.ssafy.hw.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.hw.model.dao.NewsApiDao;
import com.ssafy.hw.model.dto.NewsApiResponseDto;
import com.ssafy.hw.model.dto.NewsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
public class NewsServiceImpl implements NewsService {
	private final NewsApiDao newsApiDao;
	
	public NewsServiceImpl(NewsApiDao newsApiDao) {
		this.newsApiDao = newsApiDao;
	}

	@Override
	public List<NewsDto> searchNews(String keyword, String dong)  throws Exception {
		NewsApiResponseDto newsApiResponseDto = newsApiDao.searchNewsDto(keyword);
		List<NewsDto> aptList = newsApiResponseDto.getNewsResponseDtoWithImages();
		if(aptList.size() > 0 || dong == null) return aptList;
		// 아파트 결과가 없으면 동 결과를 반환
		newsApiResponseDto = newsApiDao.searchNewsDto(dong);
		List<NewsDto> dongList = newsApiResponseDto.getNewsResponseDtoWithImages();
		return dongList;
	}
}
