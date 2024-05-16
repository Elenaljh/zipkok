package com.ssafy.hw.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.ssafy.hw.util.JsoupCrawling;

import lombok.Getter;

@Getter
public class NewsApiResponseDto {

    @JsonSetter("items")
    private List<NewsItemDto> items;

    public List<NewsItemDto> getOnlyNaverNews() {
    	return items.stream()
                .collect(Collectors.toList());
    	//네이버뉴스만
//        return items.stream()
//            .filter(item -> item.getLink().startsWith("https://n.news"))
//            .collect(Collectors.toList());
    }

    public List<NewsDto> getNewsResponseDtoWithImages() {
    	return getOnlyNaverNews().stream()
    			.map(item -> item.toNewsResponseDto(new JsoupCrawling()))
    			.filter(NewsDto::validateImageLink)
    			.limit(10L)
    			.collect(Collectors.toList());
    	//이미지만
//        return getOnlyNaverNews().stream()
//            .map(item -> item.toNewsResponseDto(new JsoupCrawling()))
//            .filter(NewsDto::validateImageLink)
//            .limit(10L)
//            .collect(Collectors.toList());
    }
}