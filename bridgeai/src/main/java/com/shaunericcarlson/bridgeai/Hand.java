package com.shaunericcarlson.bridgeai;

public abstract class Hand {
    
    public Hand() {
        // TODO Auto-generated constructor stub
    }
    
    public abstract int getMinimumHcp();
    public abstract int getMaximumHcp();
    public abstract int getSpadesLength();
    public abstract int getHeartsLength();
    public abstract int getDiamondsLength();
    public abstract int getClubsLength();
}
