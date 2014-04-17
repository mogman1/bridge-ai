package com.shaunericcarlson.bridgeai;

public enum Suit {
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES,
    NOTRUMP;
    
    public static Suit TranslateShortString(String suit) {
        if ("c".equalsIgnoreCase(suit)) {
            return Suit.CLUBS;
        } else if ("d".equalsIgnoreCase(suit)) {
            return Suit.DIAMONDS;
        } else if ("h".equalsIgnoreCase(suit)) {
            return Suit.HEARTS;
        } else if ("s".equalsIgnoreCase(suit)) {
            return Suit.SPADES;
        } else {
            return Suit.NOTRUMP;
        }
    }
    
    public String toString() {
        String s = "";
        switch (this) {
            case CLUBS:
                s = "C"; break;
            case DIAMONDS:
                s = "D"; break;
            case HEARTS:
                s = "H"; break;
            case SPADES:
                s = "S"; break;
            case NOTRUMP:
                s = "N"; break;
        }
        
        return s;
    }
}
