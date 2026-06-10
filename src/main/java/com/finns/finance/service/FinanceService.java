package com.finns.finance.service;

import com.finns.common.exception.ResourceNotFoundException;
import com.finns.finance.dto.CardDTO;
import com.finns.finance.dto.FinanceCount;
import com.finns.finance.dto.FinanceDTO;
import com.finns.finance.mapper.FinanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
@Transactional(readOnly = true)
public class FinanceService {
    private final FinanceMapper financeMapper;

    public List<FinanceDTO> getDepositList() {
        return financeMapper.getDepositProducts();
    }

    public List<FinanceDTO> getSavingsList() {
        return financeMapper.getinstallProducts();
    }

    public FinanceDTO getProductByNo(Long financeProductNo) {
        FinanceDTO product = financeMapper.selectOneProduct(financeProductNo);
        if (product == null) {
            throw new ResourceNotFoundException("금융상품을 찾을 수 없습니다.");
        }
        return product;
    }

    public FinanceDTO getHighestIntrRateForDeposit() {
        FinanceDTO product = financeMapper.selectHighestIntrRateForDeposit();
        if (product == null) {
            throw new ResourceNotFoundException("예금 상품을 찾을 수 없습니다.");
        }
        return product;
    }

    public FinanceDTO getHighestIntrRateForSavings() {
        FinanceDTO product = financeMapper.selectHighestIntrRateForSavings();
        if (product == null) {
            throw new ResourceNotFoundException("적금 상품을 찾을 수 없습니다.");
        }
        return product;
    }

    public FinanceCount getTopDepositProductByUsers() {
        FinanceCount topProduct = financeMapper.selectTopDepositProductByUsers();
        if (topProduct == null) {
            throw new ResourceNotFoundException("저장된 예금 상품이 없습니다.");
        }
        return topProduct;
    }

    public FinanceCount getTopSavingsProductByUsers() {
        FinanceCount topProduct = financeMapper.selectTopSavingsProductByUsers();
        if (topProduct == null) {
            throw new ResourceNotFoundException("저장된 적금 상품이 없습니다.");
        }
        return topProduct;
    }

    public List<CardDTO> getCardList() {
        return financeMapper.getCardProducts();
    }

    public CardDTO getCardByNo(Long cardNo) {
        CardDTO card = financeMapper.selectOneCard(cardNo);
        if (card == null) {
            throw new ResourceNotFoundException("카드 상품을 찾을 수 없습니다.");
        }
        return card;
    }

    public List<FinanceDTO> getProductsByUser(Long userNo) {
        List<FinanceDTO> products = financeMapper.selectProductsByUser(userNo);
        return products == null ? Collections.emptyList() : products;
    }
}
