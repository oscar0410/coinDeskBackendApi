package com.yunyang.coindeskbackendapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "currency_mapping")
public class CurrencyMappingEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "chinese_name")
    private String currencyCName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
