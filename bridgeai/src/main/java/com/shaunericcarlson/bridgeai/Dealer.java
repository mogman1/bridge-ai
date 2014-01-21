package com.shaunericcarlson.bridgeai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dealer {
    private List<Card> deck;
    
    public Dealer() {
        this.deck = new ArrayList<Card>();
        for (int i = 0; i < 52; i++) {
            if (i < 13) {
                this.deck.add(new Card((i % 13) + 2, Suit.CLUBS));
            } else if (i < 26) {
                this.deck.add(new Card((i % 13) + 2, Suit.DIAMONDS));
            } else if (i < 39) {
                this.deck.add(new Card((i % 13) + 2, Suit.HEARTS));
            } else {
                this.deck.add(new Card((i % 13) + 2, Suit.SPADES));
            }
        }
    }
    
    public Hand[] deal() {
        this.shuffle();
        List<Card> hand = new ArrayList<Card>();
        Hand[] hands = new Hand[4];
        int i = 0;
        for (Card c : this.deck) {
            hand.add(new Card(c));
            if (hand.size() == 13) {
                hands[i++] = new Hand(hand);
                hand.clear();
            }
        }
        
        return hands;
    }
    
    private void shuffle() {
        //Recommendation is to shuffle a deck 7 times ;-)
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
    }
}
