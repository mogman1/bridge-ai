package com.shaunericcarlson.bridgeai.hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.shaunericcarlson.bridgeai.Card;
import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.Suit;

public class OpenHand extends Hand {
    public List<Card> clubs;
    public List<Card> diamonds;
    public List<Card> hearts;
    public List<Card> spades;
    private int hcp;
    
    public OpenHand() {
        this.hcp = -1;
        this.clubs = new ArrayList<Card>();
        this.diamonds = new ArrayList<Card>();
        this.hearts = new ArrayList<Card>();
        this.spades = new ArrayList<Card>();
    }
    
    public OpenHand(List<Card> cards) {
        this();
        if (cards.size() != 13) throw new RuntimeException("Invalid number of cards for a hand[" + cards.size() + "]");
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
    
    public OpenHand(String shape) {
        this();
        Suit s = Suit.SPADES;
        int suitCounter = 1;
        for (int i = 0; i < shape.length(); i++) {
            if (shape.charAt(i) == '|') {
                suitCounter++;
                if (suitCounter == 2) {
                    s = Suit.HEARTS;
                } else if (suitCounter == 3) {
                    s = Suit.DIAMONDS;
                } else {
                    s = Suit.CLUBS;
                }
            } else {
                switch(s) {
                    case SPADES:
                        this.spades.add(new Card(shape.substring(i, i + 1), s)); break;
                    case HEARTS:
                        this.hearts.add(new Card(shape.substring(i, i + 1), s)); break;
                    case DIAMONDS:
                        this.diamonds.add(new Card(shape.substring(i, i + 1), s)); break;
                    default:
                        this.clubs.add(new Card(shape.substring(i, i + 1), s)); break;
                }
            }
        }
        
        Collections.sort(this.clubs, Collections.reverseOrder());
        Collections.sort(this.diamonds, Collections.reverseOrder());
        Collections.sort(this.hearts, Collections.reverseOrder());
        Collections.sort(this.spades, Collections.reverseOrder());
    }
    
    public int getHighCardPoints() {
        if (this.hcp == -1) {
            //count HCP
            this.hcp = 0;
            for (Card c : this.clubs)    this.hcp += c.value;
            for (Card c : this.diamonds) this.hcp += c.value;
            for (Card c : this.hearts)   this.hcp += c.value;
            for (Card c : this.spades)   this.hcp += c.value;
        }
        
        return this.hcp;
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
    
    public String prettyString() {
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
    
    @Override
    public String toString() {
        String s = "";
        for (Card c : this.spades) s += c.getRank();
        s += ".";
        for (Card c : this.hearts) s += c.getRank();
        s += ".";
        for (Card c : this.diamonds) s += c.getRank();
        s += ".";
        for (Card c : this.clubs) s += c.getRank();
        
        return s;
    }

    @Override
    public int getMinimumHcp() {
        return this.getHighCardPoints();
    }

    @Override
    public int getMaximumHcp() {
        return this.getHighCardPoints();
    }

    @Override
    public int getSpadesLength() {
        return this.spades.size();
    }

    @Override
    public int getHeartsLength() {
        return this.hearts.size();
    }

    @Override
    public int getDiamondsLength() {
        return this.diamonds.size();
    }

    @Override
    public int getClubsLength() {
        return this.clubs.size();
    }
}
