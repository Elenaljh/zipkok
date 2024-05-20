package com.ssafy.hw.model.dao;

import com.ssafy.hw.model.dto.AptDetail;
import com.ssafy.hw.model.dto.AptTransaction;
import com.ssafy.hw.model.dto.OfficeDto;
import com.ssafy.hw.model.dto.PriceAvgDto;
import com.ssafy.hw.model.dto.SchoolDto;
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
    public List<OfficeDto> getOffice(Map<String, Double> param);
    //학교
    public List<SchoolDto> getSchool(Map<String, Double> param);
    //인구 구조

    //맛집..?????
}