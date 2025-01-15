package com.yunyang.coindeskbackendapi.service;

import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;
import com.yunyang.coindeskbackendapi.entity.param.CreateCurrencyMappingParam;
import com.yunyang.coindeskbackendapi.entity.param.UpdateCurrencyMappingParam;
import com.yunyang.coindeskbackendapi.entity.vo.CurrencyMappingVO;
import com.yunyang.coindeskbackendapi.repository.CurrencyMappingRepository;
import com.yunyang.coindeskbackendapi.restClient.CoindeskApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyMappingRepository currencyMappingRepository;

    @Autowired
    private CoindeskApiClient coindeskApiClient;

    public List<CurrencyMappingVO> getCurrencyList() {
        // find all from db
        List<CurrencyMappingEntity> currencyMappingEntityList = currencyMappingRepository.findAll();
        // convert CurrencyMappingEntity to CurrencyMappingVO
        return currencyMappingEntityList.stream().filter(Objects::nonNull).map(CurrencyMappingVO::new).toList();
    }

    public CurrencyMappingVO getCurrency(int id) {
        CurrencyMappingEntity currencyMappingEntity = currencyMappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found"));
        return new CurrencyMappingVO(currencyMappingEntity);
    }

    public CurrencyMappingVO addCurrency(CreateCurrencyMappingParam param) {
        CurrencyMappingEntity currencyMappingEntity = new CurrencyMappingEntity();
        currencyMappingEntity.setCurrencyCode(param.getCode());
        currencyMappingEntity.setCurrencyCName(param.getName());
        currencyMappingEntity = currencyMappingRepository.save(currencyMappingEntity);
        return new CurrencyMappingVO(currencyMappingEntity);
    }

    public CurrencyMappingVO updateCurrency(int id, UpdateCurrencyMappingParam param) {
        CurrencyMappingEntity currencyMappingEntity = currencyMappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found"));
        currencyMappingEntity.setCurrencyCode(param.getCode());
        currencyMappingEntity.setCurrencyCName(param.getName());
        currencyMappingEntity = currencyMappingRepository.save(currencyMappingEntity);
        return new CurrencyMappingVO(currencyMappingEntity);
    }

    public void deleteCurrency(int id) {
        currencyMappingRepository.deleteById(id);
    }

    public Map<String, Object> getTransformedCoindeskData() {
        Map<String, Object> coindeskDataMap = coindeskApiClient.getCoindeskData();
        Map<String, Object> transformedData = new HashMap<>();

        for (String key : coindeskDataMap.keySet()) {
            CurrencyMappingEntity mapping = currencyMappingRepository.findByCurrencyCode(key);
            if (mapping != null) {
                Map<String, Object> currencyData = (Map<String, Object>) coindeskDataMap.get(key);
                transformedData.put(mapping.getCurrencyCName(), currencyData);
            }
        }

        return transformedData;
    }
}
