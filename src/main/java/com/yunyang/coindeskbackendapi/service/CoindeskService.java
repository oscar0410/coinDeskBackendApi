package com.yunyang.coindeskbackendapi.service;

import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskApiResponseVO;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskTransformedVO;
import com.yunyang.coindeskbackendapi.repository.CurrencyMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoindeskService {

    @Value("${coindesk.api.url}")
    private String baseUrl;

    @Autowired
    private CurrencyMappingRepository currencyMappingRepository;

    public CoindeskApiResponseVO getCoindeskData() {
        return getCoindeskDataFromApi();
    }

    public CoindeskTransformedVO getTransformedCoindeskData() {
        // get data from coindesk api
        CoindeskApiResponseVO response = getCoindeskDataFromApi();
        // time
        CoindeskApiResponseVO.CoindeskTime coindeskTime = response.getTime();
        // bpi data
        Map<String, CoindeskApiResponseVO.CoindeskBpi> coindeskBpiMap = response.getBpi();

        // vo
        CoindeskTransformedVO transformedData = new CoindeskTransformedVO();

        // formatted time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        transformedData.setUpdatedTime(ZonedDateTime.parse(coindeskTime.getUpdatedISO()).format(formatter));
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

    private CoindeskApiResponseVO getCoindeskDataFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseUrl+ "/v1/bpi/currentprice.json", CoindeskApiResponseVO.class);
    }


    private Map<String,String> getCurrencyMapping() {
        Map<String,String> currencyMapping = new HashMap<>();
        List<CurrencyMappingEntity> currencyMappingEntities =  currencyMappingRepository.findAll();
        for (CurrencyMappingEntity entity : currencyMappingEntities) {
            currencyMapping.put(entity.getCurrencyCode(), entity.getCurrencyCName());
        }
        return currencyMapping;
    }
}
