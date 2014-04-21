package com.shaunericcarlson.bridgeai.hand;

import com.shaunericcarlson.bridgeai.Hand;

public class HiddenHand extends Hand {
    private int minimumHcp;
    private int maximumHcp;
    private int spadesLength;
    private int heartsLength;
    private int diamondsLength;
    private int clubsLength;
    
    public HiddenHand(int minHcp, int maxHcp, int spades, int hearts, int diamonds, int clubs) {
        this.minimumHcp = minHcp;
        this.maximumHcp = maxHcp;
        this.spadesLength = spades;
        this.heartsLength = hearts;
        this.diamondsLength = diamonds;
        this.clubsLength = clubs;
    }
    
    public HiddenHand(int minHcp, int maxHcp, String shape) {
        this.maximumHcp = maxHcp;
        this.minimumHcp = minHcp;
        
        String[] suits = shape.split("\\.");
        if (suits.length > 0) this.spadesLength = suits[0].length();
        if (suits.length > 1) this.heartsLength = suits[1].length();
        if (suits.length > 2) this.diamondsLength = suits[2].length();
        if (suits.length > 3) this.clubsLength = suits[3].length();
    }
    
    @Override
    public int getMinimumHcp() {
        return this.minimumHcp;
    }

    @Override
    public int getMaximumHcp() {
        return this.maximumHcp;
    }

    @Override
    public int getSpadesLength() {
        return this.spadesLength;
    }

    @Override
    public int getHeartsLength() {
        return this.heartsLength;
    }

    @Override
    public int getDiamondsLength() {
        return this.diamondsLength;
    }

    @Override
    public int getClubsLength() {
        return this.clubsLength;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.getSpadesLength(); i++) s += "*";
        s += "|";
        for (int i = 0; i < this.getHeartsLength(); i++) s += "*";
        s += "|";
        for (int i = 0; i < this.getDiamondsLength(); i++) s += "*";
        s += "|";
        for (int i = 0; i < this.getClubsLength(); i++) s += "*";
        
        return s;
    }
}
