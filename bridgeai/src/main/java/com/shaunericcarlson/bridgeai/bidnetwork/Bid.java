package com.shaunericcarlson.bridgeai.bidnetwork;

public enum Bid {
    CLUB,
    DIAMOND,
    HEART,
    SPADE,
    NOTRUMP,
    DOUBLE,
    REDOUBLE,
    PASS,
    
    /**
     * For use in bidding algorithm, indicates that the proper bid cannot be determined
     * by the current neural network
     */
    UNKNOWN,
    
    /**
     * Only used when a bid does not exist, as in that player has not yet had a chance to bid.
     * Any other usage is invalid.
     */
    NONE;
    
    public int level;
    
    public static Bid getFromChar(char b) {
        Bid bid;
        switch(b) {
            case 'S':
            case 's':
                bid = SPADE; break;
            case 'C':
            case 'c':
                bid = CLUB; break;
            case 'D':
            case 'd':
                bid = DIAMOND; break;
            case 'H':
            case 'h':
                bid = HEART; break;
            case 'N':
            case 'n':
                bid = NOTRUMP; break;
            case 'X':
            case 'x':
                bid = DOUBLE; break;
            case 'R':
            case 'r':
                bid = REDOUBLE; break;
            case 'P':
            case 'p':
                bid = PASS; break;
            case '-':
                bid = NONE; break;
            default:
                bid = UNKNOWN;
        }
        
        return bid;
    }
    
    @Override
    public String toString() {
        String s;
        switch (this) {
            case CLUB:
                s = "C"; break;
            case DIAMOND:
                s = "D"; break;
            case HEART:
                s = "H"; break;
            case SPADE:
                s = "S"; break;
            case NOTRUMP:
                s = "N"; break;
            case DOUBLE:
                s = "X"; break;
            case REDOUBLE:
                s = "R"; break;
            case PASS:
                s = "P"; break;
            case NONE:
                s = "-"; break;
            default:
                s = "U"; break;
        }
        
        return s;
    }
}