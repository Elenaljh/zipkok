package com.ssafy.hw.model.service;

import com.google.gson.Gson;
import com.ssafy.hw.feign.GoogleAuthApi;
import com.ssafy.hw.feign.GoogleUserApi;
import com.ssafy.hw.model.dto.oauth.GoogleAuthRequestDto;
import com.ssafy.hw.model.dto.oauth.GoogleAuthResponseDto;
import com.ssafy.hw.model.dto.oauth.GoogleUserInfoResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SocialLoginServiceImpl implements SocialLoginService{
    private final GoogleAuthApi googleAuthApi;
    private final GoogleUserApi googleUserApi;
    public SocialLoginServiceImpl(GoogleAuthApi googleAuthApi, GoogleUserApi googleUserApi) {
        this.googleAuthApi = googleAuthApi;
        this.googleUserApi = googleUserApi;
    }

    @Value("${google.client_id}")
    private String clientKey;
    @Value("${google.clientSecret}")
    private String clientSecret;
    @Value("${google.redirect_uri}")
    private String redirectUri;
    @Value("${google.grant_type}")
    private String grantType;

    @Override
    public GoogleAuthResponseDto getAccessToken(String code) {
        GoogleAuthRequestDto googleAuthRequestDto = new GoogleAuthRequestDto();
        googleAuthRequestDto.setCode(code);
        googleAuthRequestDto.setClient_id(clientKey);
        googleAuthRequestDto.setClientSecret(clientSecret);
        googleAuthRequestDto.setRedirect_uri(redirectUri);
        googleAuthRequestDto.setGrant_type(grantType);

        ResponseEntity<?> response = googleAuthApi.getAccessToken(googleAuthRequestDto);
//        System.out.println(response.toString());

        return new Gson().fromJson(
                response.getBody().toString(),
                GoogleAuthResponseDto.class
        );
    }

    @Override
    public GoogleUserInfoResponseDto getUserInfo(String accessToken) {
        ResponseEntity<?> response = googleUserApi.getUserInfo(accessToken);
//        System.out.println(response.getBody().toString());

        return new Gson().fromJson(
                response.getBody().toString(),
                GoogleUserInfoResponseDto.class
        );
    }
}
