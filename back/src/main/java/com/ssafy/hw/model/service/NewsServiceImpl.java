package com.ssafy.hw.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.hw.model.dao.NewsApiDao;
import com.ssafy.hw.model.dto.NewsApiResponseDto;
import com.ssafy.hw.model.dto.NewsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
	private final NewsApiDao newsApiDao;

	@Override

	public List<NewsDto> searchNews(String keyword)  throws Exception {
		NewsApiResponseDto newsApiResponseDto = newsApiDao.searchNewsDto(keyword);
		return newsApiResponseDto.getNewsResponseDtoWithImages();
	}
}
