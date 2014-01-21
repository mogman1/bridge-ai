package com.shaunericcarlson.bridgeai;

public class Card implements Comparable<Card> {
    public Suit suit;
    public int rank;
    public int value;
    
    /**
     * 
     * @param rank 2-10, J:11, Q:12, K:13, A:14
     * @param suit
     */
    public Card(int rank, Suit suit) {
        if (rank < 2 || rank > 14) throw new RuntimeException("Invalid rank value [" + rank + "], must be between 2 and 14 inclusive");
        this.suit = suit;
        this.rank = rank;
        this.value = (rank > 10) ? rank - 10 : 0;
    }
    
    public Card(Card c) {
        this.suit = c.suit;
        this.rank = c.rank;
        this.value = c.value;
    }
    
    public int compareTo(Card o) {
        int c = this.suit.compareTo(o.suit);
        if (c == 0) {
            if (this.rank > o.rank) {
                c = 1;
            } else if (this.rank < o.rank) {
                c = -1;
            }
        }
        
        return c;
    }
    
    public String getRank() {
        String val;
        switch(this.rank) {
            case 10:
                val = "T"; break;
            case 11:
                val = "J"; break;
            case 12:
                val = "Q"; break;
            case 13:
                val = "K"; break;
            case 14:
                val = "A"; break;
            default:
                val = this.rank + ""; break;
        }
        
        return val;
    }
    
    @Override
    public String toString() {
        return this.getRank() + this.suit;
    }
}    
