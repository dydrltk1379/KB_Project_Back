package com.finns.finance.service;

import com.finns.card.pagination.PageResponse;
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
    private static final int MAX_PAGE_SIZE = 100;

    private final FinanceMapper financeMapper;

    public PageResponse<FinanceDTO> getDepositList(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("size는 1 이상 " + MAX_PAGE_SIZE + " 이하여야 합니다.");
        }
        int offset = (page - 1) * size;
        List<FinanceDTO> products = financeMapper.getDepositProducts(offset, size);
        long totalElements = financeMapper.countDepositProducts();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PageResponse<>(products, totalPages, totalElements, size, page);
    }

    public PageResponse<FinanceDTO> getSavingsList(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("size는 1 이상 " + MAX_PAGE_SIZE + " 이하여야 합니다.");
        }
        int offset = (page - 1) * size;
        List<FinanceDTO> products = financeMapper.getinstallProducts(offset, size);
        long totalElements = financeMapper.countInstallProducts();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PageResponse<>(products, totalPages, totalElements, size, page);
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

    public PageResponse<CardDTO> getCardList(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("size는 1 이상 " + MAX_PAGE_SIZE + " 이하여야 합니다.");
        }
        int offset = (page - 1) * size;
        List<CardDTO> cards = financeMapper.getCardProducts(offset, size);
        long totalElements = financeMapper.countCardProducts();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PageResponse<>(cards, totalPages, totalElements, size, page);
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
