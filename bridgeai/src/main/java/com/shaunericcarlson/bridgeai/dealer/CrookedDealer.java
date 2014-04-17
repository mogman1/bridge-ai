package com.shaunericcarlson.bridgeai.dealer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.shaunericcarlson.bridgeai.Card;
import com.shaunericcarlson.bridgeai.Dealer;
import com.shaunericcarlson.bridgeai.Direction;
import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.Suit;
import com.shaunericcarlson.bridgeai.hand.HiddenHand;
import com.shaunericcarlson.bridgeai.hand.OpenHand;

public class CrookedDealer extends Dealer {
    private final static int RHO = 0;
    private final static int PARTNER = 1;
    private final static int LHO = 2;
    
    private final static int SPADES = 0;
    private final static int HEARTS = 1;
    private final static int DIAMONDS = 2;
    private final static int CLUBS = 3;
    
    private Set<Card> spadeHonors;
    private Set<Card> spadeFiller;
    private Set<Card> heartHonors;
    private Set<Card> heartFiller;
    private Set<Card> diamondHonors;
    private Set<Card> diamondFiller;
    private Set<Card> clubHonors;
    private Set<Card> clubFiller;
    private String myHandShape;
    private int myMinPoints;
    private int myMaxPoints;
    
    private String[] playerHandShapes = {null, null, null};
    private int[] playerMinHcps = {-1, -1, -1};
    private int[] playerMaxHcps = {-1, -1, -1};

    
    public CrookedDealer(String shape) {
        this(shape, 40, 0);
    }
    
    public CrookedDealer(String shape, int maxPoints) {
        this(shape, maxPoints, 0);
    }
    
    public CrookedDealer(String shape, int maxPoints, int minPoints) {
        super(Direction.NORTH);
        this.myHandShape = shape;
        this.myMaxPoints = maxPoints;
        this.myMinPoints = minPoints;
    }
    
    private void addHand(String shape, int minHcp, int maxHcp, int PLAYER) {
        if (shape.length() != 16) throw new RuntimeException("Invalid hand shape [" + shape + "]");
        this.playerHandShapes[PLAYER] = shape;
        this.playerMinHcps[PLAYER] = minHcp;
        this.playerMaxHcps[PLAYER] = maxHcp;
    }
    
    public void addLho(String shape) {
        this.addHand(shape, -1, -1, LHO);
    }
    
    public void addLho(String shape, int minHcp, int maxHcp) {
        this.addHand(shape, minHcp, maxHcp, LHO);
    }
    
    public void addPartner(String shape) {
        this.addHand(shape, -1, -1, PARTNER);
    }
    
    public void addPartner(String shape, int minHcp, int maxHcp) {
        this.addHand(shape, minHcp, maxHcp, PARTNER);
    }
    
    public void addRho(String shape) {
        this.addHand(shape, -1, -1, RHO);
    }
    
    public void addRho(String shape, int minHcp, int maxHcp) {
        this.addHand(shape, minHcp, maxHcp, RHO);
    }
    
    private Hand[] buildHands() {
        Hand openHand = this.buildOpenHand();
        int playerHands[][] = this.buildPlayerHands(openHand);
        int remainingPoints = 40 - openHand.getMaximumHcp();
        
        Hand[] hands = {openHand, null, null, null};
        for (int i = 0; i < playerHands.length; i++) {
            int minHcp = (this.playerMinHcps[i] == -1) ? 0 : this.playerMinHcps[i];
            int maxHcp = (this.playerMaxHcps[i] == -1) ? remainingPoints : this.playerMaxHcps[i];
            hands[i+1] = new HiddenHand(minHcp, maxHcp, playerHands[i][SPADES], playerHands[i][HEARTS], playerHands[i][DIAMONDS], playerHands[i][CLUBS]);
        }
        
        return hands;
    }
    
    private Hand buildOpenHand() {
        while (true) {
            this.buildSuits();
            List<Card> cards = new ArrayList<Card>();
            Suit s = Suit.SPADES;
            int suitCounter = 1;
            for (int i = 0; i < this.myHandShape.length(); i++) {
                if (this.myHandShape.charAt(i) == '|') {
                    suitCounter++;
                    if (suitCounter == 2) {
                        s = Suit.HEARTS;
                    } else if (suitCounter == 3) {
                        s = Suit.DIAMONDS;
                    } else {
                        s = Suit.CLUBS;
                    }
                } else {
                    cards.add(this.getCard(this.myHandShape.substring(i, i + 1), s));
                }
            }
            
            Hand h = new OpenHand(cards);
            if (h.getMaximumHcp() <= this.myMaxPoints && h.getMinimumHcp() >= this.myMinPoints) return h;
        }
    }
    
    private int[][] buildPlayerHands(Hand openHand) {
        int ISSET = 4;
        int playerHands[][] = new int[3][5];
        int suit = SPADES;
        int unspecifiedHands = 3;
        for (int i = 0; i < this.playerHandShapes.length; i++) {
            if (this.playerHandShapes[i] != null) {
                unspecifiedHands--;
                playerHands[i][ISSET] = 1;
                for (char c : this.playerHandShapes[i].toCharArray()) {
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
                    Dealer.MAX_SUIT_LENGTH - (openHand.getSpadesLength() + playerHands[0][SPADES] + playerHands[1][SPADES] + playerHands[2][SPADES]),
                    Dealer.MAX_SUIT_LENGTH - (openHand.getHeartsLength() + playerHands[0][HEARTS] + playerHands[1][HEARTS] + playerHands[2][HEARTS]),
                    Dealer.MAX_SUIT_LENGTH - (openHand.getDiamondsLength() + playerHands[0][DIAMONDS] + playerHands[1][DIAMONDS] + playerHands[2][DIAMONDS]),
                    Dealer.MAX_SUIT_LENGTH - (openHand.getClubsLength() + playerHands[0][CLUBS] + playerHands[1][CLUBS] + playerHands[2][CLUBS]),
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
        
        return playerHands;
    }
    
    private void buildSuits() {
        Suit[] suits = {Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS};
        String[] hArr = {"A", "K", "Q", "J", "T"};
        String[] fArr = {"9", "8", "7", "6", "5", "4", "3", "2"};
        for (Suit s : suits) {
            Set<Card> honors = new HashSet<Card>();
            Set<Card> filler = new HashSet<Card>();
            for (String c : hArr) honors.add(new Card(c, s));
            for (String c : fArr) filler.add(new Card(c, s));
            
            if (s == Suit.SPADES) {
                this.spadeHonors = honors;
                this.spadeFiller = filler;
            } else if (s == Suit.HEARTS) {
                this.heartHonors = honors;
                this.heartFiller = filler;
            } else if (s ==Suit.DIAMONDS) {
                this.diamondHonors = honors;
                this.diamondFiller = filler;
            } else {
                this.clubHonors = honors;
                this.clubFiller = filler;
            }
        }
    }
    
    @Override
    public Hand[] deal() {
        this.buildSuits();
        return this.buildHands();
    }
    
    private Card getCard(String rank, Suit s) {
        Set<Card> suitHonors;
        Set<Card> suitFiller;
        if (s == Suit.SPADES) {
            suitHonors = this.spadeHonors;
            suitFiller = this.spadeFiller;
        } else if (s == Suit.HEARTS) {
            suitHonors = this.heartHonors;
            suitFiller = this.heartFiller;
        } else if (s == Suit.DIAMONDS) {
            suitHonors = this.diamondHonors;
            suitFiller = this.diamondFiller;
        } else {
            suitHonors = this.clubHonors;
            suitFiller = this.clubFiller;
        }
        
        Card c;
        if (rank.toLowerCase().equals("h")) {
            //retrieve an honors card from suit and remove it from set
            List<Card> cards = new ArrayList<Card>(suitHonors);
            Collections.shuffle(cards);
            c = cards.get(0);
        } else if (rank.toLowerCase().equals("x")) {
            //retrieve a filler card and remove it from set
            List<Card> cards = new ArrayList<Card>(suitFiller);
            Collections.shuffle(cards);
            c = cards.get(0);
        } else if (rank.toLowerCase().equals("*")) {
            //retrieve *any* card from suit and remove it from appropriate set
            Set<Card> all = new HashSet<Card>(suitHonors);
            all.addAll(suitFiller);
            List<Card> cards = new ArrayList<Card>(all);
            Collections.shuffle(cards);
            c = cards.get(0);
        } else {
            //retrieve specific card from suit and remove it from appropriate set
            c = new Card(rank, s);
        }
        
        //card could have come from honors or filler, try removing it from both
        suitHonors.remove(c);
        suitFiller.remove(c);
        
        return c;
    }
}
