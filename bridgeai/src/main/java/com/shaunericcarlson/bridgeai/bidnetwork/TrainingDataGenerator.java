package com.shaunericcarlson.bridgeai.bidnetwork;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.dealer.CrookedDealer;

public class TrainingDataGenerator {
    public int minHcp;
    public int maxHcp;
    public String shape;
    
    public int rhoMinHcp;
    public int rhoMaxHcp;
    public String rhoShape;
    
    public int partnerMinHcp;
    public int partnerMaxHcp;
    public String partnerShape;
    
    public int lhoMinHcp;
    public int lhoMaxHcp;
    public String lhoShape;
    
    public String rhoBid;
    public String partnerBid;
    public String lhoBid;
    public String myLastBid;
    public String bidShouldBe;
    
    public TrainingDataGenerator(String data) {
        String[] components = data.split("\\s+");
        if (components.length != 13) throw new RuntimeException("Invalid training data");
        int i = 0;
        this.shape = components[i++];
        String[] range = components[i++].split("\\-");
        this.minHcp = Integer.parseInt(range[0]);
        this.maxHcp = Integer.parseInt(range[1]);
        
        this.rhoShape = components[i++];
        range = components[i++].split("\\-");
        this.rhoMinHcp = (range.length == 2) ? Integer.parseInt(range[0]) : -1;
        this.rhoMaxHcp = (range.length == 2) ? Integer.parseInt(range[1]) : -1;
        
        this.partnerShape = components[i++];
        range = components[i++].split("\\-");
        this.partnerMinHcp = (range.length == 2) ? Integer.parseInt(range[0]) : -1;
        this.partnerMaxHcp = (range.length == 2) ? Integer.parseInt(range[1]) : -1;
        
        this.lhoShape = components[i++];
        range = components[i++].split("\\-");
        this.lhoMinHcp = (range.length == 2) ? Integer.parseInt(range[0]) : -1;
        this.lhoMaxHcp = (range.length == 2) ? Integer.parseInt(range[1]) : -1;
        
        this.bidShouldBe  = components[i++];
        this.rhoBid       = components[i++];
        this.partnerBid   = components[i++];
        this.lhoBid       = components[i++];
        this.myLastBid    = components[i++];
    }
    
    private CrookedDealer createDealer() {
        CrookedDealer dealer = new CrookedDealer(this.shape, this.maxHcp, this.minHcp);
        if (!this.rhoShape.equals("-")) {
            if (this.rhoMinHcp == -1) {
                dealer.addRho(this.rhoShape);
            } else {
                dealer.addRho(this.rhoShape, this.rhoMinHcp, this.rhoMaxHcp);
            }
        }
        
        if (!this.partnerShape.equals("-")) {
            if (this.partnerMinHcp == -1) {
                dealer.addPartner(this.partnerShape);
            } else {
                dealer.addPartner(this.partnerShape, this.partnerMinHcp, this.partnerMaxHcp);
            }
        }
        
        if (!this.lhoShape.equals("-")) {
            if (this.lhoMinHcp == -1) {
                dealer.addLho(this.lhoShape);
            } else {
                dealer.addLho(this.lhoShape, this.lhoMinHcp, this.lhoMaxHcp);
            }
        }
        
        return dealer;
    }
    
    public Collection<BidTrainingData> generate(int number) {
        Map<String, BidTrainingData> trainingData = new HashMap<String, BidTrainingData>();
        CrookedDealer dealer = this.createDealer();
        while (trainingData.size() < number) {
            Hand[] hands = dealer.deal();
            String s = hands[0].toString();
            for (int j = 1; j < hands.length; j++)
                s += " " + hands[j] + " " + hands[j].getMinimumHcp() + "-" + hands[j].getMaximumHcp();
            
            s += " " + this.lhoBid + " " + this.partnerBid + " " + this.rhoBid + " " + this.myLastBid;
            s += " " + this.bidShouldBe;
            if (!trainingData.containsKey(s)) trainingData.put(s, new BidTrainingData(s));
        }
        
        return trainingData.values();
    }
}
