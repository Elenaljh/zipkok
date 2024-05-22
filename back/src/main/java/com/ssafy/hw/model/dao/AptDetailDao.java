package com.ssafy.hw.model.dao;

import com.ssafy.hw.model.dto.*;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AptDetailDao {
    //아파트 코드로 apt_info 조회
    public AptDetail searchDetailByCode(String aptCode);
    //준공년도, 주소, 동 수, 세대수, CCTV, 부대시설, 정류장
    //아파트 코드로 transaction_real 조회
    public List<AptTransaction> searchTransactionByCode(@Param("aptCode") String aptCode, @Param("type") String type); //전세, 매매 여부
    //아파트 이름으로 transaction_real 조회
    public List<AptTransaction> searchTransactionByName(@Param("aptName") String aptName, @Param("type") String type);

    //아파트 가격 평균
    public PriceAvgDto getAverage(@Param("aptCode") String aptCode, @Param("type") String type);

    //관공서
    public Integer getOffice(@Param("lng") Double lng, @Param("lat") Double lat);
    //학교
    public Integer getSchool(@Param("lng") Double lng, @Param("lat") Double lat);
    //cctv
    public Integer getCCTV(String aptCode);
    //모범음식점
    public Integer getRestaurant(String dongCode);
    //도시공원
    public Integer getPark(@Param("lng") Double lng, @Param("lat") Double lat);

    //인구 구조
    public PopulationDto getPopulation(String bjdCode);
    //여성인구, 남성인구, 성별 인구
    public PopulationBySexDto getFemalePopulation(String bjdCode);
    public PopulationBySexDto getMalePopulation(String bjdCode);
    public PopulationSexRatioDto getPopRatio(String bjdCode);

    //동 이름
    public String getDongbyCode(String bjdCode);
}
