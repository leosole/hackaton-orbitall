package com.orbitallcorp.hack21.cards.controllers;

import com.orbitallcorp.hack21.cards.domains.Card;
import com.orbitallcorp.hack21.cards.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    public boolean cardNumberIsValid(String cardNumber){
        cardNumber = cardNumber.replaceAll("\\s",""); // remove espaços em branco
        if(cardNumber.length() == 16 && NumberUtils.isNumber(cardNumber) ) {
            return true;
        }
        return false;
    }
    @PostMapping
    public ResponseEntity<Card> save(@RequestBody Card card) {
        String cardNumber = card.getCardNumber();
        if( cardNumberIsValid(cardNumber) ) {  // testa se cartão tem quantidade certa de dígitos
            Card savedCard = cardService.save((card));
            return new ResponseEntity(savedCard, HttpStatus.CREATED);
        }
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Card>> findAll() {
        List<Card> cards = cardService.findAll();
        return ResponseEntity.ok(cards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        String res = cardService.deleteById((id));
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> updateById(@PathVariable Long id, @RequestBody Card card) {
        card.setId(id);
        String cardNumber = card.getCardNumber();
        if( cardNumberIsValid(cardNumber) ) {  // testa se cartão tem quantidade certa de dígitos
            Card updatedCard = cardService.updateById(id, card);
            if (updatedCard != null) {
                return new ResponseEntity(updatedCard, HttpStatus.OK);
            }
            return new ResponseEntity(card, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(card, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> findById(@PathVariable Long id){
        Card foundCard = cardService.findById(id).get();
        return new ResponseEntity(foundCard, HttpStatus.OK);
    }
}