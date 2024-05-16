package com.ssafy.hw.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDto {
	private String title;
	private String link;
	private String company;
	private String imageLink;
	private String detail;
	
    public boolean validateImageLink() {
        return imageLink != null && !imageLink.isEmpty();
    }
}
