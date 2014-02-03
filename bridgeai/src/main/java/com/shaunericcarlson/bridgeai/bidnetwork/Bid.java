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
    UNKNOWN;
//    NONE;
    
    
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
//            case NONE:
            default:
                s = "-"; break;
//                s = "U"; break;
        }
        
        return s;
    }
}
