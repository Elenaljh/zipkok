package com.ssafy.hw.feign;

import com.ssafy.hw.config.FeignConfig;
import com.ssafy.hw.model.dto.oauth.GoogleAuthRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "googleAuth", url="https://oauth2.googleapis.com", configuration = {FeignConfig.class})
public interface GoogleAuthApi {
    @PostMapping("/token")
    ResponseEntity<String> getAccessToken(@RequestBody GoogleAuthRequestDto requestDto);
}
