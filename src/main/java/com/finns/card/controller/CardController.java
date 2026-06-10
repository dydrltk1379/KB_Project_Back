package com.finns.card.controller;

import com.finns.card.dto.Card;
import com.finns.card.pagination.PageResponse;
import com.finns.card.service.CardService;
import com.finns.common.util.SecurityUtils;
import com.finns.security.account.domain.CustomUser;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(value = "CardController", tags = "카드")
@PropertySource({"classpath:/application.properties"})
@CrossOrigin(origins = "http://localhost:5173")
public class CardController {
    private final CardService cardService;

    @GetMapping("/cards")
    public PageResponse<Card> getCards(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return cardService.getCards(page, size);
    }

    @GetMapping("/cards/{no}")
    public ResponseEntity<Card> getCard(@PathVariable("no") long cardNo) {
        return ResponseEntity.ok(cardService.getCardById(cardNo));
    }

    @GetMapping("/users/{no}/recommendCards/{num}")
    public ResponseEntity<List<Card>> recommendCardsByUser(@PathVariable("no") long userNo,
                                                           @PathVariable("num") int num) {
        return ResponseEntity.ok(cardService.getRecommendNCards(userNo, num));
    }

    @GetMapping("/users/{no}/cards")
    public ResponseEntity<List<Card>> getEnrolledCardsByUser(
            @PathVariable("no") Long userNo,
            @AuthenticationPrincipal CustomUser customUser) {
        SecurityUtils.assertSameUser(userNo, customUser);
        return ResponseEntity.ok(cardService.getCardsByUser(userNo));
    }
}
