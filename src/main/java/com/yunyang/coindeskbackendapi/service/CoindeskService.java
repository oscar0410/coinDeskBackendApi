package com.yunyang.coindeskbackendapi.service;

import com.yunyang.coindeskbackendapi.apiClient.CoindeskApiClient;
import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskApiResponseVO;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskTransformedVO;
import com.yunyang.coindeskbackendapi.repository.CurrencyMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CoindeskService {

    @Autowired
    private CoindeskApiClient apiClient;

    @Autowired
    private CurrencyMappingRepository currencyMappingRepository;

    public CoindeskApiResponseVO getCoindeskData() {
        return apiClient.getBpiCurrencyData();
    }

    public CoindeskTransformedVO getTransformedCoindeskData() {
        // get data from coindesk api
        CoindeskApiResponseVO response = apiClient.getBpiCurrencyData();
        // time
        CoindeskApiResponseVO.CoindeskTime coindeskTime = response.getTime();
        // bpi data
        Map<String, CoindeskApiResponseVO.CoindeskBpi> coindeskBpiMap = response.getBpi();

        // vo
        CoindeskTransformedVO transformedData = new CoindeskTransformedVO();

        // formatted time
        Timestamp updatedTime = coindeskTime.getUpdatedISO();
        Date updatedDate = new Date(updatedTime.getTime());
        transformedData.setUpdatedTime(updatedDate);
        // currencies
        // get currency mapping from database
        Map<String, String> currencyMap = getCurrencyMapping();
        List<CoindeskTransformedVO.CoindeskTransformedCurrencyVO> currencies = new ArrayList<>();
        for (Map.Entry<String, CoindeskApiResponseVO.CoindeskBpi> entry : coindeskBpiMap.entrySet()) {
            CoindeskApiResponseVO.CoindeskBpi coindeskBpi = entry.getValue();
            CoindeskTransformedVO.CoindeskTransformedCurrencyVO currency = new CoindeskTransformedVO.CoindeskTransformedCurrencyVO();
            currency.setCode(coindeskBpi.getCode());
            currency.setChineseName(currencyMap.get(coindeskBpi.getCode()));
            currency.setRate(coindeskBpi.getRateFloat());
            currencies.add(currency);
        }
        transformedData.setCurrencies(currencies);
        return transformedData;

    }


    private Map<String, String> getCurrencyMapping() {
        Map<String, String> currencyMapping = new HashMap<>();
        List<CurrencyMappingEntity> currencyMappingEntities = currencyMappingRepository.findAll();
        for (CurrencyMappingEntity entity : currencyMappingEntities) {
            currencyMapping.put(entity.getCode(), entity.getChineseName());
        }
        return currencyMapping;
    }
}
