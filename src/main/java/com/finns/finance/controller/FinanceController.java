package com.finns.finance.controller;

import com.finns.card.pagination.PageResponse;
import com.finns.common.util.SecurityUtils;
import com.finns.finance.dto.CardDTO;
import com.finns.finance.dto.FinanceCount;
import com.finns.finance.dto.FinanceDTO;
import com.finns.finance.service.FinanceService;
import com.finns.security.account.domain.CustomUser;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Api(value = "FinanceController", tags = "금융상품 정보")
@CrossOrigin(origins = "http://localhost:5173")
public class FinanceController {

    private final FinanceService financeService;

    @GetMapping("/{financeProductType}")
    public ResponseEntity<PageResponse<FinanceDTO>> getProductList(
            @PathVariable("financeProductType") String financeProductType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        if ("01".equals(financeProductType)) {
            return ResponseEntity.ok(financeService.getDepositList(page, size));
        }
        if ("02".equals(financeProductType)) {
            return ResponseEntity.ok(financeService.getSavingsList(page, size));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/no/{financeProductNo}")
    public ResponseEntity<FinanceDTO> getFinanceProduct(@PathVariable("financeProductNo") Long financeProductNo) {
        return ResponseEntity.ok(financeService.getProductByNo(financeProductNo));
    }

    @GetMapping("/highest/deposit")
    public ResponseEntity<FinanceDTO> getHighestIntrRateForDeposit() {
        return ResponseEntity.ok(financeService.getHighestIntrRateForDeposit());
    }

    @GetMapping("/highest/savings")
    public ResponseEntity<FinanceDTO> getHighestIntrRateForSavings() {
        return ResponseEntity.ok(financeService.getHighestIntrRateForSavings());
    }

    @GetMapping("/top/deposit")
    public ResponseEntity<FinanceCount> getTopDepositByUsers() {
        return ResponseEntity.ok(financeService.getTopDepositProductByUsers());
    }

    @GetMapping("/top/savings")
    public ResponseEntity<FinanceCount> getTopSavingsByUsers() {
        return ResponseEntity.ok(financeService.getTopSavingsProductByUsers());
    }

    @GetMapping("/card")
    public ResponseEntity<PageResponse<CardDTO>> getCardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(financeService.getCardList(page, size));
    }

    @GetMapping("/card/{cardNo}")
    public ResponseEntity<CardDTO> getCardProduct(@PathVariable("cardNo") Long cardNo) {
        return ResponseEntity.ok(financeService.getCardByNo(cardNo));
    }

    @GetMapping("/users/{no}/products")
    public ResponseEntity<List<FinanceDTO>> getEnrolledProductsByUser(
            @PathVariable("no") Long userNo,
            @AuthenticationPrincipal CustomUser customUser) {
        SecurityUtils.assertSameUser(userNo, customUser);
        return ResponseEntity.ok(financeService.getProductsByUser(userNo));
    }
}
