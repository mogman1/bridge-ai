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
    
    public int level = 0;
    
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
    
    public int toInt() {
        int convertedBid = 0;
        switch(this) {
            case UNKNOWN:
            case NONE:
                convertedBid = 0; break;
            case PASS:
                convertedBid = 3; break;
            case DOUBLE:
                convertedBid = 6; break;
            case REDOUBLE:
                convertedBid = 9; break;
            case CLUB:
                convertedBid = 10 * this.level + 0; break;
            case DIAMOND:
                convertedBid = 10 * this.level + 2; break;
            case HEART:
                convertedBid = 10 * this.level + 4; break;
            case SPADE:
                convertedBid = 10 * this.level + 6; break;
            case NOTRUMP:
                convertedBid = 10 * this.level + 8; break;
        }
        
        return convertedBid;
    }
    
    public String altToString() {
        return this.level + " " + this.toString();
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