package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dto.oauth.GoogleAuthRequestDto;
import com.ssafy.hw.model.dto.oauth.GoogleAuthResponseDto;
import com.ssafy.hw.model.dto.oauth.GoogleUserInfoResponseDto;

public interface SocialLoginService {
    public GoogleAuthResponseDto getAccessToken(String code);
    public GoogleUserInfoResponseDto getUserInfo(String accessToken);
}
