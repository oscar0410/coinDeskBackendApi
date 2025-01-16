package com.yunyang.coindeskbackendapi.entity.vo;

import java.math.BigDecimal;
import java.util.List;

public class CoindeskTransformedVO {

    private String updatedTime;

    private List<CoindeskTransformedCurrencyVO> currencies;

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<CoindeskTransformedCurrencyVO> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CoindeskTransformedCurrencyVO> currencies) {
        this.currencies = currencies;
    }

    public static class CoindeskTransformedCurrencyVO {

        private String code;
        private String chineseName;
        private BigDecimal rate;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }
    }
}
