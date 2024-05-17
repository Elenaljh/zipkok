package com.ssafy.hw.model.dao;

import com.ssafy.hw.model.dto.AptDetail;
import com.ssafy.hw.model.dto.AptTransaction;

public interface AptDetailDao {
    //아파트 코드로 apt_info 조회
    public AptDetail searchDetailByCode(String aptCode);
    //준공년도, 주소, 동 수, 세대수, CCTV, 부대시설, 정류장
    //아파트 코드로 transaction_real 조회
    public AptTransaction searchTransactionByCode(String aptCode, String type); //전세, 매매 여부
    //아파트 이름으로 transaction_real 조회
    public AptTransaction searchTransactionByName(String aptName, String type);

    //주소 기반으로 대기정보 가져오기

    //공원

    //관공서

    //학교

    //인구 구조

    //맛집..?????
}
