package com.shaunericcarlson.bridgeai.dealer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.shaunericcarlson.bridgeai.Card;
import com.shaunericcarlson.bridgeai.Dealer;
import com.shaunericcarlson.bridgeai.Direction;
import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.Suit;
import com.shaunericcarlson.bridgeai.hand.OpenHand;

public class FairDealer extends Dealer {
    private List<Card> deck;
    
    public FairDealer() {
        super(Direction.NORTH);
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
    
    @Override
    public Hand[] deal() {
        this.shuffle();
        List<Card> hand = new ArrayList<Card>();
        Hand[] hands = new Hand[4];
        int i = 0;
        for (Card c : this.deck) {
            hand.add(new Card(c));
            if (hand.size() == 13) {
                hands[i++] = new OpenHand(hand);
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
