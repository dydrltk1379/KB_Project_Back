package com.finns.card.service;

import com.finns.amountByCategory.service.AmountByCategoryService;
import com.finns.card.dto.Card;
import com.finns.card.dto.RecommendNCardRequestDTO;
import com.finns.card.mapper.CardMapper;
import com.finns.card.pagination.PageResponse;
import com.finns.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
@Transactional(readOnly = true)
public class CardService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final int MAX_RECOMMEND_COUNT = 20;

    private final CardMapper cardMapper;
    private final AmountByCategoryService amountByCategoryService;

    public PageResponse<Card> getCards(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("size는 1 이상 " + MAX_PAGE_SIZE + " 이하여야 합니다.");
        }

        int offset = (page - 1) * size;
        List<Card> cards = cardMapper.selectAllCards(offset, size);
        long totalElements = cardMapper.countCards();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageResponse<>(cards, totalPages, totalElements, size, page);
    }

    public Card getCardById(long cardNo) {
        Card card = cardMapper.selectCardById(cardNo);
        if (card == null) {
            throw new ResourceNotFoundException("카드를 찾을 수 없습니다.");
        }
        return card;
    }

    public List<Card> getRecommendNCards(Long userNo, int num) {
        if (num < 1 || num > MAX_RECOMMEND_COUNT) {
            throw new IllegalArgumentException("num은 1 이상 " + MAX_RECOMMEND_COUNT + " 이하여야 합니다.");
        }

        String topCategoryByUser = amountByCategoryService.calculateTopCategory(userNo);
        if (topCategoryByUser == null) {
            return Collections.emptyList();
        }

        String cardCategory = matchingCategory(topCategoryByUser);
        RecommendNCardRequestDTO recommendNCardRequestDTO = new RecommendNCardRequestDTO(cardCategory, num);
        return cardMapper.selectRecommendNCards(recommendNCardRequestDTO);
    }

    private String matchingCategory(String categoryByUser) {
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("식비 · 카페", "식비");
        categoryMap.put("술 · 유흥", "식비");
        categoryMap.put("쇼핑", "여가");
        categoryMap.put("미용", "여가");
        categoryMap.put("문화 · 여행", "여가");

        return categoryMap.getOrDefault(categoryByUser, categoryByUser);
    }

    public List<Card> getCardsByUser(Long userNo) {
        List<Card> cards = cardMapper.selectCardsByUser(userNo);
        return cards == null ? Collections.emptyList() : cards;
    }
}
