package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dto.AptDetail;
import com.ssafy.hw.model.dto.AptTransaction;
import com.ssafy.hw.model.dto.OfficeDto;
import com.ssafy.hw.model.dto.PriceAvgDto;
import com.ssafy.hw.model.dto.SchoolDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AptDetailService {
    //아파트 코드로 apt_info 조회
    public AptDetail searchDetailByCode(String aptCode);
    //준공년도, 주소, 동 수, 세대수, CCTV, 부대시설, 정류장
    //아파트 코드로 transaction_real 조회
    public List<AptTransaction> searchTransactionByCode(String aptCode, String type); //전세, 매매 여부
    //아파트 이름으로 transaction_real 조회
    public List<AptTransaction> searchTransactionByName(String aptName, String type);

    //평균가 조회
    public PriceAvgDto getAverage(String aptCode, String type);

    //관공서
    public List<OfficeDto> getOffice(double lat, double lng);
    //학교
    public List<SchoolDto> getSchool(double lat, double lng);
}