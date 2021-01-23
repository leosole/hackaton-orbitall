package com.orbitallcorp.hack21.cards.services;

import com.orbitallcorp.hack21.cards.domains.Card;
import com.orbitallcorp.hack21.cards.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card save(Card card) {
        return cardRepository.save((card));
    }

    public List<Card> findAll() {
        List<Card> cards = new ArrayList<>();
        cardRepository.findAll().forEach(cards :: add);
        return cards;
    }

    public String deleteById(Long id) {
        cardRepository.deleteById((id));
        return "Card " + id + " deleted";
    }

    public Card updateById(Long id, Card card){
        if( cardRepository.existsById(id) ){
            return cardRepository.save((card));
        }
        return null;
    }

    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }
}