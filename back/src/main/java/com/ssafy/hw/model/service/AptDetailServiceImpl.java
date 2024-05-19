package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dao.AptDetailDao;
import com.ssafy.hw.model.dto.AptDetail;
import com.ssafy.hw.model.dto.AptTransaction;
import com.ssafy.hw.model.dto.PriceAvgDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AptDetailServiceImpl implements AptDetailService {
    @Value("${airstation.api.key}")
    private String key;

    private final AptDetailDao dao;
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


}
