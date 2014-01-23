package com.shaunericcarlson.bridgeai.bidnetwork;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.shaunericcarlson.bridgeai.Dealer;
import com.shaunericcarlson.bridgeai.Hand;

public class TrainingDataGenerator {
    private Dealer dealer;
    private Map<String, BidTrainingData> trainingData;
    private String rhoBid;
    private String partnerBid;
    private String lhoBid;
    private String myLastBid;
    private String bidShouldBe;
    
    public TrainingDataGenerator(Dealer dealer, String bidShouldBe) {
        this.dealer = dealer;
        this.trainingData = new HashMap<String, BidTrainingData>();
        this.lhoBid = this.rhoBid = this.partnerBid = this.myLastBid = "-";
        this.bidShouldBe = bidShouldBe;
    }
    
    public void addBids(String rhoBid) {
        this.addBids(rhoBid, "-", "-");
    }
    
    public void addBids(String rhoBid, String partnerBid) {
        this.addBids(rhoBid, partnerBid, "-");
    }
    
    public void addBids(String rhoBid, String partnerBid, String lhoBid) {
        this.rhoBid = rhoBid;
        this.partnerBid = partnerBid;
        this.lhoBid = lhoBid;
    }
    
    public Collection<BidTrainingData> generate(int number) {
        while (this.trainingData.size() < number) {
            Hand[] hands = this.dealer.deal();
            String s = hands[0].toString();
            for (int j = 1; j < hands.length; j++)
                s += " " + hands[j] + " " + hands[j].getMinimumHcp() + "-" + hands[j].getMaximumHcp();
            
            s += " " + this.lhoBid + " " + this.partnerBid + " " + this.rhoBid + " " + this.myLastBid;
            s += " " + this.bidShouldBe;
            if (!this.trainingData.containsKey(s)) this.trainingData.put(s, new BidTrainingData(s));
        }
            
        return this.trainingData.values();
    }
}
