package com.yunyang.coindeskbackendapi.repository;

import com.yunyang.coindeskbackendapi.entity.CurrencyMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyMappingRepository extends JpaRepository<CurrencyMappingEntity, Integer> {

    CurrencyMappingEntity findByCode(String currencyCode);
}
