package com.yunyang.coindeskbackendapi.restClient;


import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CoindeskApiClient {

    @Value("${coindesk.api.url}")
    private String baseUrl;

    public Map<String, Object> getCoindeskData() {
        RestTemplate restTemplate = new RestTemplate();
        String targetUrl = baseUrl + "/v1/bpi/currentprice.json";
        Map<String, Object> response =restTemplate.getForObject(targetUrl, Map.class);
        if (response == null || !response.containsKey("bpi")) {
            throw new RuntimeException("Failed to fetch Coindesk data");
        }

        Map<String, Object> bpi = (Map<String, Object>) response.get("bpi");
        return bpi;

    }
}
