package com.yunyang.coindeskbackendapi.controller;

import com.yunyang.coindeskbackendapi.entity.param.CreateCurrencyMappingParam;
import com.yunyang.coindeskbackendapi.entity.param.UpdateCurrencyMappingParam;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskApiResponseVO;
import com.yunyang.coindeskbackendapi.entity.vo.CoindeskTransformedVO;
import com.yunyang.coindeskbackendapi.entity.vo.CurrencyMappingVO;
import com.yunyang.coindeskbackendapi.service.CoindeskService;
import com.yunyang.coindeskbackendapi.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CoindeskService coindeskService;
    /**
     * 取得 CurrencyMapping 清單
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CurrencyMappingVO>> getCurrencyList() {
        List<CurrencyMappingVO> result = currencyService.getCurrencyList();
        return ResponseEntity.ok(result);
    }

    /**
     * 取得指定的 CurrencyMapping
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyMappingVO> getCurrency(@PathVariable("id") int id) {
        CurrencyMappingVO result = currencyService.getCurrency(id);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增 CurrencyMapping
     *
     * @param param
     * @return
     */
    @PostMapping
    public ResponseEntity<CurrencyMappingVO> addCurrency(@RequestBody CreateCurrencyMappingParam param) {
        CurrencyMappingVO result = currencyService.addCurrency(param);
        return ResponseEntity.ok(result);
    }


    /**
     * 更新 CurrentMapping
     *
     * @param id
     * @param param
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CurrencyMappingVO> updateCurrency(@PathVariable("id") int id, @RequestBody UpdateCurrencyMappingParam param) {
        CurrencyMappingVO result = currencyService.updateCurrency(id, param);
        return ResponseEntity.ok(result);
    }

    /**
     * 刪除 CurrencyMapping
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable("id") int id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.ok().build();
    }

    // Coindesk API Integration
    @GetMapping("/coindesk")
    public ResponseEntity<CoindeskApiResponseVO> getTransformedData() {
        return ResponseEntity.ok(coindeskService.getCoindeskData());
    }

    @GetMapping("/coindesk/transform")
    public ResponseEntity<CoindeskTransformedVO> getCoindeskData() {
        return ResponseEntity.ok(coindeskService.getTransformedCoindeskData());
    }


}
