package com.orbitallcorp.hack21.cards.controllers;

import com.orbitallcorp.hack21.cards.domains.Card;
import com.orbitallcorp.hack21.cards.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<Card> save(@RequestBody Card card) {
        Card savedCard = cardService.save((card));
        return new ResponseEntity(savedCard, HttpStatus.CREATED);
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
        Card updatedCard = cardService.updateById(id, card);
        if (updatedCard != null){
            return new ResponseEntity(updatedCard, HttpStatus.OK);
        }
        return new ResponseEntity(card, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> findById(@PathVariable Long id){
        Card foundCard = cardService.findById(id).get();
        return new ResponseEntity(foundCard, HttpStatus.OK);
    }
}