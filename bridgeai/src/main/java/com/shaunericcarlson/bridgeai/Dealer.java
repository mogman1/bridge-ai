package com.shaunericcarlson.bridgeai;

public abstract class Dealer {
    protected final static int MAX_HCP = 40;
    protected final static int MAX_SUIT_LENGTH = 13;
    
    public abstract Hand[] deal();
}
