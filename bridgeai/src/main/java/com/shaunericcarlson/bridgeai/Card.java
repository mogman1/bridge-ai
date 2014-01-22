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
    
    public Card(String rank, Suit suit) {
        switch (rank.charAt(0)) {
            case 'A':
            case 'a':
                this.rank = 14; break;
            case 'K':
            case 'k':
                this.rank = 13; break;
            case 'Q':
            case 'q':
                this.rank = 12; break;
            case 'J':
            case 'j':
                this.rank = 11; break;
            case 'T':
            case 't':
                this.rank = 10; break;
            case '9':
            case '8':
            case '7':
            case '6':
            case '5':
            case '4':
            case '3':
            case '2':
                this.rank = Integer.parseInt(rank.substring(0, 1)); break;
            default:
                throw new RuntimeException("Invalid rank [" + rank + "]");
        }
        
        this.suit = suit;
        this.value = (this.rank > 10) ? this.rank - 10 : 0;
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
    
    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (o.getClass().equals(Card.class)) {
            if (((Card)o).suit == this.suit && ((Card)o).rank == this.rank) isEqual = true;
        }
        
        return isEqual;
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
    public int hashCode() {
        return (this.suit.ordinal() * 10) + this.rank;
    }
    
    @Override
    public String toString() {
        return this.getRank() + this.suit;
    }
}    
