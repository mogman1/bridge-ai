package com.shaunericcarlson.bridgeai.doubledummy;

import java.util.HashMap;
import java.util.Map;

import com.shaunericcarlson.bridgeai.Direction;
import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.Suit;
import com.shaunericcarlson.bridgeai.bidnetwork.Bid;

public class DoubleDummyResult {
    private Map<Direction, Map<Suit, Integer>> data;
    private Hand opener;
    private Hand responder;
    
    public DoubleDummyResult(Hand opener, Hand responder) {
        this.opener = opener;
        this.responder = responder;
        this.data = new HashMap<Direction, Map<Suit, Integer>>();
        this.data.put(Direction.NORTH, new HashMap<Suit, Integer>());
        this.data.put(Direction.SOUTH, new HashMap<Suit, Integer>());
        this.data.put(Direction.EAST, new HashMap<Suit, Integer>());
        this.data.put(Direction.WEST, new HashMap<Suit, Integer>());
    }
    
    public Hand getOpener() {
        return this.opener;
    }
    
    public Hand getResponder() {
        return this.responder;
    }
    
    public int getTricks(Direction dir, Suit trump) {
        return this.data.get(dir).get(trump);
    }
    
    public Bid getOptimalContract(Bid contract) {
        final int LEVEL_ADJUST = 6;
        int level = 0;
        Suit optimal = Suit.CLUBS;
        Bid optimalBid;
        Suit[] suits = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES, Suit.NOTRUMP};
        Direction[] dirs = {Direction.NORTH, Direction.SOUTH};
        for (Suit s : suits) {
            for (Direction d : dirs) {
                int tricks = this.getTricks(d, s);
                if (tricks > level) {
                    level = tricks;
                    optimal = s;
                } else if (tricks == level) {
                    //if equal number of minor or major suit tricks can be made, prefer the suit of the passed-in bid
                    if (s == Suit.CLUBS && optimal == Suit.DIAMONDS && contract == Bid.DIAMOND) continue;
                    if (s == Suit.DIAMONDS && optimal == Suit.CLUBS && contract == Bid.CLUB)    continue;
                    if (s == Suit.HEARTS && optimal == Suit.SPADES && contract == Bid.SPADE)    continue;
                    if (s == Suit.SPADES && optimal == Suit.HEARTS && contract == Bid.HEART)    continue;
                    
                    level = tricks;
                    optimal = s;
                }
            }
        }
        
        if (level > LEVEL_ADJUST) {
            switch (optimal) {
                case CLUBS:     optimalBid = Bid.CLUB; break;
                case DIAMONDS:  optimalBid = Bid.DIAMOND; break;
                case HEARTS:    optimalBid = Bid.HEART; break;
                case SPADES:    optimalBid = Bid.SPADE; break;
                case NOTRUMP:
                default:        optimalBid = Bid.NOTRUMP; break;
            }
            
            optimalBid.level = level - LEVEL_ADJUST;
        } else {
            optimalBid = Bid.PASS;
            optimalBid.level = 0;
        }
        
        
        return optimalBid;
    }
    
    public void setTrick(Direction dir, Suit trump, int number) {
        this.data.get(dir).put(trump, number);
    }
}
