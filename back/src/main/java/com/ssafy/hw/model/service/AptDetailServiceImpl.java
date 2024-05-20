package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dao.AptDetailDao;
import com.ssafy.hw.model.dto.AptDetail;
import com.ssafy.hw.model.dto.AptTransaction;
import com.ssafy.hw.model.dto.OfficeDto;
import com.ssafy.hw.model.dto.PriceAvgDto;
import com.ssafy.hw.model.dto.SchoolDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AptDetailServiceImpl implements AptDetailService {
    public final AptDetailDao dao;
    public AptDetailServiceImpl(AptDetailDao dao) {
        this.dao = dao;
    }

    @Override
    public AptDetail searchDetailByCode(String aptCode) {
        return dao.searchDetailByCode(aptCode);
    }

    @Override
    public List<AptTransaction> searchTransactionByCode(String aptCode, String type) {
        return dao.searchTransactionByCode(aptCode, type);
    }

    @Override
    public List<AptTransaction> searchTransactionByName(String aptName, String type) {
        return dao.searchTransactionByName(aptName, type);
    }

    @Override
    public PriceAvgDto getAverage(String aptCode, String type) {
        return dao.getAverage(aptCode, type);
    }

    @Override
    public List<OfficeDto> getOffice(double lat, double lng) {
        Map<String, Double> param = new HashMap<>();
        param.put("lat", lat);
        param.put("lng", lng);
        return dao.getOffice(param);
    }

    @Override
    public List<SchoolDto> getSchool(double lat, double lng) {
        Map<String, Double> param = new HashMap<>();
        param.put("lat", lat);
        param.put("lng", lng);
        return dao.getSchool(param);
    }

}
