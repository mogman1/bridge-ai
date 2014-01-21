package com.shaunericcarlson.bridgeai;

public enum Suit {
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES,
    NOTRUMP;
    
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
