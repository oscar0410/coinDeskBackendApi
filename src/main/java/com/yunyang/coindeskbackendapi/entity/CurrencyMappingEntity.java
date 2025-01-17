package com.yunyang.coindeskbackendapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "currency_mapping")
public class CurrencyMappingEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "currency_code")
    private String code;
    @Column(name = "chinese_name")
    private String chineseName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String currencyCode) {
        this.code = currencyCode;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String currencyCName) {
        this.chineseName = currencyCName;
    }
}
