package com.shaunericcarlson.bridgeai;

import com.shaunericcarlson.bridgeai.hand.HiddenHand;
import com.shaunericcarlson.bridgeai.hand.OpenHand;

public class BiddingAnalyzer {
    private Direction me;
    private Direction currentBidder;
    private Hand north;
    private Hand east;
    private Hand south;
    private Hand west;
    
    private OpenHand myHand;
    private HiddenHand rho;
    private HiddenHand partner;
    private HiddenHand lho;
    
    private final static int SPADES = 0;
    private final static int HEARTS = 1;
    private final static int DIAMONDS = 2;
    private final static int CLUBS = 3;

    public BiddingAnalyzer(OpenHand hand, Direction myDir, Direction opener) {
        this.myHand = hand;
        this.me = myDir;
        this.currentBidder = opener;
        switch (myDir) {
            case NORTH:
                this.north = hand; break;
            case EAST:
                this.east = hand; break;
            case SOUTH:
                this.south = hand; break;
            case WEST:
                this.west = hand; break;
        }
        
        this.buildPlayerHands();
    }
    
    private void buildPlayerHands() {
        int ISSET = 4;
        int playerHands[][] = new int[3][5];
        int suit = SPADES;
        int unspecifiedHands = 3;
        String[] playerHandShapes = {null, null, null};
        for (int i = 0; i < playerHandShapes.length; i++) {
            if (playerHandShapes[i] != null) {
                unspecifiedHands--;
                playerHands[i][ISSET] = 1;
                for (char c : playerHandShapes[i].toCharArray()) {
                    if (c == '|') {
                        suit++;
                    } else {
                        playerHands[i][suit]++;
                    }
                }
            }
        }
        
        if (unspecifiedHands > 0) {
            int remainingSuits[] = {
                    Dealer.MAX_SUIT_LENGTH - (this.myHand.getSpadesLength() + playerHands[0][SPADES] + playerHands[1][SPADES] + playerHands[2][SPADES]),
                    Dealer.MAX_SUIT_LENGTH - (this.myHand.getHeartsLength() + playerHands[0][HEARTS] + playerHands[1][HEARTS] + playerHands[2][HEARTS]),
                    Dealer.MAX_SUIT_LENGTH - (this.myHand.getDiamondsLength() + playerHands[0][DIAMONDS] + playerHands[1][DIAMONDS] + playerHands[2][DIAMONDS]),
                    Dealer.MAX_SUIT_LENGTH - (this.myHand.getClubsLength() + playerHands[0][CLUBS] + playerHands[1][CLUBS] + playerHands[2][CLUBS]),
            };
            
            //Divvy out remaining cards evenly.
            for (int i = 0; i < playerHands.length; i++) {
                if (playerHands[i][ISSET] == 0) {
                    playerHands[i][SPADES] = remainingSuits[SPADES] / unspecifiedHands;
                    playerHands[i][HEARTS] = remainingSuits[HEARTS] / unspecifiedHands;
                    playerHands[i][DIAMONDS] = remainingSuits[DIAMONDS] / unspecifiedHands;
                    playerHands[i][CLUBS] = remainingSuits[CLUBS] / unspecifiedHands;
                }
            }
            
            //Assign "stragglers" to hands.  At this stage it doesn't matter which hand gets what, so long as it's close to even.
            if (unspecifiedHands > 1) { //if only one was unspecified, above for loop already handled divvying out cards
                for (int i = 0; i < remainingSuits.length; i++) {
                    int t = remainingSuits[i] % unspecifiedHands;
                    int p = 0;
                    while (t > 0) {
                        if (playerHands[p][ISSET] == 0 && 
                                playerHands[p][SPADES] + playerHands[p][HEARTS] + playerHands[p][DIAMONDS] + playerHands[p][CLUBS] < 13) {
                            playerHands[p][i]++;
                            t--;
                        }
                        
                        p = (p == playerHands.length - 1) ? p : p + 1;
                    }
                }
            }
        }
        
        int minHcp = 0;
        int maxHcp = 40 - this.myHand.getMinimumHcp();
        for (int i = 0; i < playerHands.length; i++) {
            HiddenHand h = new HiddenHand(minHcp, maxHcp, playerHands[i][SPADES], playerHands[i][HEARTS], playerHands[i][DIAMONDS], playerHands[i][CLUBS]);
            switch(i) {
                case 0:
                    this.lho = h; break;
                case 1:
                    this.partner = h; break;
                default:
                    this.rho = h; break;
            }
        }
    }
}