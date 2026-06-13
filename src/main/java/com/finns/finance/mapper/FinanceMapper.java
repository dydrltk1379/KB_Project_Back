package com.finns.finance.mapper;

import com.finns.finance.dto.CardDTO;
import com.finns.finance.dto.Finance;
import com.finns.finance.dto.FinanceCount;
import com.finns.finance.dto.FinanceDTO;

import java.util.List;

public interface FinanceMapper {

    // 예금 상품을 가져오는 메서드
    List<FinanceDTO> getDepositProducts(int offset, int limit);

    // 적금 상품을 가져오는 메서드
    List<FinanceDTO> getinstallProducts(int offset, int limit);

    // 금융상품 한개 검색
    FinanceDTO selectOneProduct(Long no);

    FinanceDTO selectHighestIntrRateForDeposit();  // 예금에서 가장 높은 intr_rate2를 선택
    FinanceDTO selectHighestIntrRateForSavings();  // 적금에서 가장 높은 intr_rate2를 선택

    FinanceCount selectTopDepositProductByUsers();
    FinanceCount selectTopSavingsProductByUsers();
    // 예금/적금/카드 개수 조회
    long countDepositProducts();
    long countInstallProducts();
    long countCardProducts();

    // 카드 상품을 가져오는 메서드
    List<CardDTO> getCardProducts(int offset, int limit);

    // 카드 상품 한개 검색
    CardDTO selectOneCard(Long no);

    List<FinanceDTO> selectProductsByUser(Long userNo);
}
