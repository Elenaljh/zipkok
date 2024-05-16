package com.ssafy.hw.model.dto;

import java.util.Optional;

import org.jsoup.select.Elements;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.ssafy.hw.util.JsoupCrawling;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class NewsItemDto {

    @JsonSetter("title")
    private String title;
    @JsonSetter("link")
    private String link;
    @JsonSetter("description")
    private String description;

    public NewsDto toNewsResponseDto(JsoupCrawling jsoupCrawling) {
        String query = "#contents img";
        Optional<Elements> jsoupElements = jsoupCrawling.getJsoupElements(link, query);
        if (jsoupElements.isPresent()) {
            return NewsDto.builder()
                .imageLink(jsoupElements.get().attr("data-src"))
                .title(title)
                .link(link)
                .detail(description)
                .build();
        }
        return NewsDto.builder()
            .title(title)
            .link(link)
            .detail(description)
            .build();
    }

}