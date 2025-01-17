package com.yunyang.coindeskbackendapi.apiClient;

import com.yunyang.coindeskbackendapi.entity.vo.CoindeskApiResponseVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CoindeskApiClient {

    @Value("${coindesk.api.url}")
    private String baseUrl;

    public CoindeskApiResponseVO getBpiCurrencyData() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseUrl + "/v1/bpi/currentprice.json", CoindeskApiResponseVO.class);
    }
}
