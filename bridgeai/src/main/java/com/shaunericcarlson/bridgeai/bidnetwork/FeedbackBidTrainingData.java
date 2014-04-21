package com.shaunericcarlson.bridgeai.bidnetwork;

import java.util.LinkedList;

import com.shaunericcarlson.bridgeai.Hand;

public class FeedbackBidTrainingData {
    private final static int MAX_HISTORY = 8;
    private Hand myHand;
    private LinkedList<Bid> bidHistory;
    
    public FeedbackBidTrainingData(Hand hand) {
        this.myHand = hand;
        this.bidHistory = new LinkedList<Bid>();
    }
    
    public void addBid(Bid bid) {
        this.bidHistory.add(bid);
        if (this.bidHistory.size() > FeedbackBidTrainingData.MAX_HISTORY) this.bidHistory.remove();
    }
    
    public Bid getContract() {
        return (this.bidHistory.size() > 2) ? this.bidHistory.get(this.bidHistory.size() - 3) : Bid.PASS;
    }
    
    public double[] getInputs() {
        double[] data = {
            this.myHand.getSpadesLength(),
            this.myHand.getHeartsLength(),
            this.myHand.getDiamondsLength(),
            this.myHand.getClubsLength(),
            this.myHand.getMaximumHcp(),
            
            (this.bidHistory.size() >= 8) ? this.bidHistory.get(7).toInt() : Bid.NONE.toInt(),
            (this.bidHistory.size() >= 7) ? this.bidHistory.get(6).toInt() : Bid.NONE.toInt(),
            (this.bidHistory.size() >= 6) ? this.bidHistory.get(5).toInt() : Bid.NONE.toInt(),
            (this.bidHistory.size() >= 5) ? this.bidHistory.get(4).toInt() : Bid.NONE.toInt(),
            (this.bidHistory.size() >= 4) ? this.bidHistory.get(3).toInt() : Bid.NONE.toInt(),
            (this.bidHistory.size() >= 3) ? this.bidHistory.get(2).toInt() : Bid.NONE.toInt(),
            (this.bidHistory.size() >= 2) ? this.bidHistory.get(1).toInt() : Bid.NONE.toInt(),
            (this.bidHistory.size() >= 1) ? this.bidHistory.get(0).toInt() : Bid.NONE.toInt(),
        };
        
        return data;
    }
    
    public Bid getLastBid() {
        return (this.bidHistory.size() > 0) ? this.bidHistory.getLast() : Bid.NONE;
    }
    
    public Bid getPenultimateBid() {
        return (this.bidHistory.size() > 1) ? this.bidHistory.get(this.bidHistory.size() - 2) : Bid.NONE;
    }
    
    public boolean isFinished() {
        Bid lastBid = this.getLastBid();
        Bid penlBid = this.getPenultimateBid();
        
        return (lastBid == Bid.PASS && penlBid == Bid.PASS);
    }
    
    public boolean isOutputValid(Bid output) {
        boolean valid = true;
        if (this.bidHistory.size() > 0) {
            Bid lastBid = this.bidHistory.getLast();
            if (lastBid == Bid.PASS && this.bidHistory.size() > 1) {
                lastBid = this.bidHistory.get(this.bidHistory.size() - 2);
            }
            
            if (output.level > lastBid.level) {
                valid = true;
            } else if (output.level == lastBid.level && output.ordinal() > lastBid.ordinal()) {
                valid = true;
            } else if (output == Bid.PASS && lastBid == Bid.PASS) {
                valid = true;
            } else {
                valid = false;
            }
        }
        
        return valid;
    }
}
