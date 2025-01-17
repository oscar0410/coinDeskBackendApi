package com.yunyang.coindeskbackendapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CoindeskTransformedVO {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "UTC")
    private Date updatedTime;

    private List<CoindeskTransformedCurrencyVO> currencies;

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
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
