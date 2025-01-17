package com.yunyang.coindeskbackendapi.entity.vo;

import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;

public class CurrencyMappingVO {

    private int currencyId;
    private String currencyCode;
    private String currencyCName;

    public CurrencyMappingVO() {
    }

    public CurrencyMappingVO(CurrencyMappingEntity entity) {
        this.currencyId = entity.getId();
        this.currencyCode = entity.getCode();
        this.currencyCName = entity.getChineseName();
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCName() {
        return currencyCName;
    }

    public void setCurrencyCName(String currencyCName) {
        this.currencyCName = currencyCName;
    }
}
