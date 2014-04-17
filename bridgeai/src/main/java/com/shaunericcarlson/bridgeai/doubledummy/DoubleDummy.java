package com.shaunericcarlson.bridgeai.doubledummy;

import java.util.HashMap;
import java.util.Map;

import com.shaunericcarlson.bridgeai.Direction;
import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.Suit;

abstract public class DoubleDummy {
    private boolean isValid;
    private Map<Direction, Map<Suit, Integer>> data;
    
    public DoubleDummy() {
        this.isValid = false;
        this.data = new HashMap<Direction, Map<Suit, Integer>>();
    }
    
    abstract protected boolean evaluate(Hand[] hands, Direction dealer, Map<Direction, Map<Suit, Integer>> data);
    
    /**
     * Attempts to do a double dummy evaluation on a deal.  If this cannot be done for any reason,
     * isValid() will return false.
     * @param hands Array of 4 hands, with the assumption being that the first element is the dealer's hand
     * @param dealer Direction of the dealer
     */
    public void evaluateDeal(Hand[] hands, Direction dealer) {
        this.data = new HashMap<Direction, Map<Suit, Integer>>();
        this.data.put(Direction.NORTH, new HashMap<Suit, Integer>());
        this.data.put(Direction.SOUTH, new HashMap<Suit, Integer>());
        this.data.put(Direction.EAST, new HashMap<Suit, Integer>());
        this.data.put(Direction.WEST, new HashMap<Suit, Integer>());
        
        this.isValid = this.evaluate(hands, dealer, this.data);
    }
    
    public int getTricks(Direction dir, Suit trump) throws Exception {
        if (this.isValid()) {
            return this.data.get(dir).get(trump);
        } else {
            throw new Exception("Double dummy data is not available");
        }
    }
    
    public boolean isValid() {
        return this.isValid;
    }
}
