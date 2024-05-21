package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dao.AptDetailDao;
import com.ssafy.hw.model.dto.*;

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
    public int getOffice(double lat, double lng) {
        Map<String, Double> param = new HashMap<>();
        param.put("lat", lat);
        param.put("lng", lng);
        return dao.getOffice(param);
    }

    @Override
    public int getSchool(double lat, double lng) {
        Map<String, Double> param = new HashMap<>();
        param.put("lat", lat);
        param.put("lng", lng);
        return dao.getSchool(param);
    }

    @Override
    public int getCCTV(String aptCode) {
        return dao.getCCTV(aptCode);
    }

    @Override
    public int getRestaurant(String dongCode) {
        return dao.getRestaurant(dongCode);
    }

    @Override
    public PopulationDto getPopulation(String bjdCode) {
        return dao.getPopulation(bjdCode);
    }

    @Override
    public Integer[] getFemalePopulation(String bjdCode) {
        PopulationBySexDto p = dao.getFemalePopulation(bjdCode);
        return new Integer[]{p.getZero(), p.getTen(), p.getTwenty(), p.getThirty(), p.getForty(), p.getFifty(), p.getOld()};
    }

    @Override
    public Integer[] getMalePopulation(String bjdCode) {
        PopulationBySexDto p = dao.getMalePopulation(bjdCode);
        return new Integer[]{p.getZero(), p.getTen(), p.getTwenty(), p.getThirty(), p.getForty(), p.getFifty(), p.getOld()};
    }

    @Override
    public Double[] getPopRatio(String bjdCode) {
        PopulationSexRatioDto p = dao.getPopRatio(bjdCode);
        return new Double[]{p.getMale(), p.getFemale()};
    }

    @Override
    public String getDongbyCode(String bjdCode) {
        return dao.getDongbyCode(bjdCode);
    }

}
