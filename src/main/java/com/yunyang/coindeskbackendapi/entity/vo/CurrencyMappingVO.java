package com.yunyang.coindeskbackendapi.entity.vo;

import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;

public class CurrencyMappingVO {

    private String code;
    private String name;

    public CurrencyMappingVO() {
    }

    public CurrencyMappingVO(CurrencyMappingEntity entity){
        this.code = entity.getCurrencyCode();
        this.name = entity.getCurrencyCName();
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
