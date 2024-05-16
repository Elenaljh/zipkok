package com.ssafy.hw.model.dao;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.ssafy.hw.model.dto.NewsApiResponseDto;

@Repository
public class NewsApiDao{
	 @Value("${news.api.client}")
	    private String newsApiClient;
    @Value("${news.api.secret}")
    private String newsApiKey;
   

    public NewsApiResponseDto searchNewsDto(String keyword) throws Exception  {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getNaverApiRequestHeaders();

        
        String url = "https://openapi.naver.com/v1/search/news.json?query=" + keyword + "&display=50";
        ResponseEntity<NewsApiResponseDto> newsApiResponseDtoEntity = restTemplate.exchange(url, HttpMethod.GET,
            new HttpEntity<>(headers), NewsApiResponseDto.class);
        if (newsApiResponseDtoEntity.getStatusCode().is2xxSuccessful()) {
            return newsApiResponseDtoEntity.getBody();
        }
        throw new Exception("searchNewsDto");
    }

    private HttpHeaders getNaverApiRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", newsApiClient);
        headers.set("X-Naver-Client-Secret", newsApiKey);
        return headers;
    }

}