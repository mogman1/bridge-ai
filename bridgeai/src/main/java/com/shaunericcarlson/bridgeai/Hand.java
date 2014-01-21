package com.shaunericcarlson.bridgeai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    public List<Card> clubs;
    public List<Card> diamonds;
    public List<Card> hearts;
    public List<Card> spades;
    private int hcpOnly;
    private int shapePoints;
    private int handValue;
    
    public Hand() {
        this.hcpOnly = 0;
        this.shapePoints = 0;
        this.handValue = -1;
        this.clubs = new ArrayList<Card>();
        this.diamonds = new ArrayList<Card>();
        this.hearts = new ArrayList<Card>();
        this.spades = new ArrayList<Card>();
    }
    
    public Hand(List<Card> cards) {
        this();
        for (Card c : cards) {
            switch (c.suit) {
                case CLUBS:
                    this.clubs.add(c); break;
                case DIAMONDS:
                    this.diamonds.add(c); break;
                case HEARTS:
                    this.hearts.add(c); break;
                case SPADES:
                    this.spades.add(c);break;
                default:
                    throw new RuntimeException("Invalid suit [" + c.suit + "]");
            }
        }
        
        Collections.sort(this.clubs, Collections.reverseOrder());
        Collections.sort(this.diamonds, Collections.reverseOrder());
        Collections.sort(this.hearts, Collections.reverseOrder());
        Collections.sort(this.spades, Collections.reverseOrder());
    }
    
    public int getHandValue() {
        if (this.handValue == -1) {
            //evaluate length
            if (this.clubs.size() == 1) this.shapePoints += 1;
            if (this.diamonds.size() == 1) this.shapePoints += 1;
            if (this.hearts.size() == 1) this.shapePoints += 1;
            if (this.spades.size() == 1) this.shapePoints += 1;
            
            if (this.clubs.size() == 0) this.shapePoints += 2;
            if (this.diamonds.size() == 0) this.shapePoints += 2;
            if (this.hearts.size() == 0) this.shapePoints += 2;
            if (this.spades.size() == 0) this.shapePoints += 2;
            
            //count HCP
            for (Card c : this.clubs) this.hcpOnly += c.value;
            for (Card c : this.diamonds) this.hcpOnly += c.value;
            for (Card c : this.hearts) this.hcpOnly += c.value;
            for (Card c : this.spades) this.hcpOnly += c.value;
            this.handValue = this.shapePoints + this.hcpOnly;
        }
        
        return this.handValue;
    }
    
    public boolean isBalanced() {
        int maxDoubletons = 1;
        boolean isBalanced = true;
        if (this.clubs.size() < 3) {
            if (this.clubs.size() == 2 && maxDoubletons > 0) {
                maxDoubletons--;
            } else {
                isBalanced = false;
            }
        }
        
        if (this.diamonds.size() < 3) {
            if (this.diamonds.size() == 2 && maxDoubletons > 0) {
                maxDoubletons--;
            } else {
                isBalanced = false;
            }
        }
        
        if (this.hearts.size() < 3) {
            if (this.hearts.size() == 2 && maxDoubletons > 0) {
                maxDoubletons--;
            } else {
                isBalanced = false;
            }
        }
        
        if (this.spades.size() < 3) {
            if (this.spades.size() == 2 && maxDoubletons > 0) {
                maxDoubletons--;
            } else {
                isBalanced = false;
            }
        }
        
        return isBalanced;
    }
    
    @Override
    public String toString() {
        String s = " S \t H \t D \t C \n";
        s += "---\t---\t---\t---";
        for (int i = 0; i < 13; i++) {
            String row = "\n";
            row += (this.spades.size() > i) ? " " + this.spades.get(i).getRank() + " \t" : "   \t";
            row += (this.hearts.size() > i) ? " " + this.hearts.get(i).getRank() + " \t" : "   \t";
            row += (this.diamonds.size() > i) ? " " + this.diamonds.get(i).getRank() + " \t" : "   \t";
            row += (this.clubs.size() > i) ? " " + this.clubs.get(i).getRank() + " \t" : "   \t";
            if (!row.trim().equals("")) s += row;
        }
        
        return s;
    }
}
