package com.finns.amountByCategory.service;

import com.finns.Mbti;
import com.finns.amountByCategory.dto.AmountByCategory;
import com.finns.amountByCategory.mapper.AmountByCategoryMapper;
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
public class AmountByCategoryService {

    private final AmountByCategoryMapper mapper;

    public List<AmountByCategory> getAmountsForCategoryByUser(Long userNo) {
        List<AmountByCategory> amounts = mapper.selectAllByUser(userNo);
        return amounts == null ? Collections.emptyList() : amounts;
    }

    public String calculateTopCategory(Long userNo) {
        List<AmountByCategory> amountByCategories = getAmountsForCategoryByUser(userNo);
        if (amountByCategories.isEmpty()) {
            return null;
        }
        String topCategory = null;
        double maxPoint = 0;
        for(AmountByCategory amountByCategory : amountByCategories) {
            double point = Mbti.calculatePoints(amountByCategory.getCategory(), amountByCategory.getAmount());

            if(maxPoint < point){
                maxPoint = point;
                topCategory = amountByCategory.getCategory();
            }
        }

        return topCategory;
    }

}
