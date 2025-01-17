package com.yunyang.coindeskbackendapi.service;

import com.yunyang.coindeskbackendapi.apiClient.CoindeskApiClient;
import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskApiResponseVO;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskTransformedVO;
import com.yunyang.coindeskbackendapi.repository.CurrencyMappingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoindeskServiceTest {

    @InjectMocks
    private CoindeskService coindeskService;

    @Mock
    private CurrencyMappingRepository currencyMappingRepository;

    @Mock
    private CoindeskApiClient coindeskApiClient;

    @Test
    void testGetTransformedCoindeskData_Success() {

        // Arrange
        Timestamp testTime = new Timestamp(System.currentTimeMillis());

        // Mock currencyMapping
        List<CurrencyMappingEntity> mockEntityList = new ArrayList<>();
        CurrencyMappingEntity mockEntity = new CurrencyMappingEntity();
        mockEntity.setId(1);
        mockEntity.setCode("USD");
        mockEntity.setChineseName("美元");
        mockEntityList.add(mockEntity);
        CurrencyMappingEntity mockEntity2 = new CurrencyMappingEntity();
        mockEntity2.setId(2);
        mockEntity2.setCode("EUR");
        mockEntity2.setChineseName("歐元");
        mockEntityList.add(mockEntity2);

        // Mock restTemplate
        CoindeskApiResponseVO mockResponse = new CoindeskApiResponseVO();
        CoindeskApiResponseVO.CoindeskTime mockTime = new CoindeskApiResponseVO.CoindeskTime();
        // mockUsdBpi
        CoindeskApiResponseVO.CoindeskBpi mockUsdBpi = new CoindeskApiResponseVO.CoindeskBpi();
        mockUsdBpi.setCode("USD");
        mockUsdBpi.setRate("1.0000");
        mockUsdBpi.setRateFloat(new BigDecimal("1.0000"));
        // mockEurBpi
        CoindeskApiResponseVO.CoindeskBpi mockEurBpi = new CoindeskApiResponseVO.CoindeskBpi();
        mockEurBpi.setCode("EUR");
        mockEurBpi.setRate("1,111");
        mockEurBpi.setRateFloat(new BigDecimal("1111.1111"));
        // mockBpi
        Map<String, CoindeskApiResponseVO.CoindeskBpi> mockBpiMap = new HashMap<>();
        mockBpiMap.put("USD", mockUsdBpi);
        mockBpiMap.put("EUR", mockEurBpi);

        mockTime.setUpdatedISO(testTime);
        mockResponse.setTime(mockTime);
        mockResponse.setBpi(mockBpiMap);

        when(currencyMappingRepository.findAll()).thenReturn(mockEntityList);
        when(coindeskApiClient.getBpiCurrencyData()).thenReturn(mockResponse);

        // Act
        CoindeskTransformedVO transformedCoindeskData = coindeskService.getTransformedCoindeskData();
        List<CoindeskTransformedVO.CoindeskTransformedCurrencyVO> resultCurrencies =  transformedCoindeskData.getCurrencies();

        CoindeskTransformedVO.CoindeskTransformedCurrencyVO usdCurrency = resultCurrencies.stream().filter(c -> c.getCode().equals("USD")).findFirst().orElse(null);
        CoindeskTransformedVO.CoindeskTransformedCurrencyVO eurCurrency = resultCurrencies.stream().filter(c -> c.getCode().equals("EUR")).findFirst().orElse(null);

        // Assert
        assertNotNull(transformedCoindeskData);
        assertEquals(testTime.getTime(), transformedCoindeskData.getUpdatedTime().getTime());
        assertEquals(2, resultCurrencies.size());
        assertNotNull(usdCurrency);
        assertNotNull(eurCurrency);
        assertEquals("美元", usdCurrency.getChineseName());
        assertEquals(new BigDecimal("1.0000"), usdCurrency.getRate());
        assertEquals("歐元", eurCurrency.getChineseName());
        assertEquals(new BigDecimal("1111.1111"), eurCurrency.getRate());
    }
}