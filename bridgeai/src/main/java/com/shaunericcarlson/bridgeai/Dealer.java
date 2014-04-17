package com.shaunericcarlson.bridgeai;

public abstract class Dealer {
    protected final static int MAX_HCP = 40;
    protected final static int MAX_SUIT_LENGTH = 13;
    protected Direction dealer;
    
    protected Dealer(Direction dealer) {
        this.dealer = dealer;
    }
    
    public abstract Hand[] deal();
    
    public Direction getDealer() {
        return this.dealer;
    }
}
