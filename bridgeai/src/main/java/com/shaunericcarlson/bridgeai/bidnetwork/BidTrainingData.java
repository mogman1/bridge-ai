package com.shaunericcarlson.bridgeai.bidnetwork;

import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.hand.HiddenHand;
import com.shaunericcarlson.bridgeai.hand.OpenHand;

public class BidTrainingData {
    private Hand myHand;
    private Hand rho;
    private Hand partner;
    private Hand lho;
    private String rhoBid;
    private String partnerBid;
    private String lhoBid;
    private String myLastBid;
    private String shouldBe;
    
    public BidTrainingData(String bidData) {
        String[] components = bidData.split("\\s+");
        if (components.length != 12) throw new RuntimeException("Invalid format for bid data [" + bidData + "]");
        
        this.myHand = new OpenHand(components[0]);
        String[] range = components[2].split("\\-");
        this.rho = new HiddenHand(Integer.parseInt(range[0]), Integer.parseInt(range[1]), components[1]);
        range = components[4].split("\\-");
        this.partner = new HiddenHand(Integer.parseInt(range[0]), Integer.parseInt(range[1]), components[3]);
        range = components[6].split("\\-");
        this.lho = new HiddenHand(Integer.parseInt(range[0]), Integer.parseInt(range[1]), components[5]);
        
        this.rhoBid = components[7];
        this.partnerBid = components[8];
        this.lhoBid = components[9];
        this.myLastBid = components[10];
        this.shouldBe = components[11];
    }
    
    private int convertBidString(String bid) {
        int convertedBid;
        switch(bid.charAt(0)) {
            case '-':
                convertedBid = 0; break;
            case 'P':
            case 'p':
                convertedBid = 3; break;
            case 'D':
            case 'd':
                convertedBid = 6; break;
            case 'R':
            case 'r':
                convertedBid = 9; break;
            default:
                convertedBid = 10 * Integer.parseInt(Character.toString(bid.charAt(0)));
                switch (bid.charAt(1)) {
                    case 'c':
                    case 'C':
                        convertedBid += 0; break; //spades
                    case 'd':
                    case 'D':
                        convertedBid += 2; break; //spades
                    case 'h':
                    case 'H':
                        convertedBid += 4; break; //spades
                    case 's':
                    case 'S':
                        convertedBid += 6; break; //spades
                    default:
                        convertedBid += 8; break; //notrump
                }
        }
        
        return convertedBid;
    }

    public double[] getInputs() {
        double[] data = {
                this.myHand.getSpadesLength(),
                this.myHand.getHeartsLength(),
                this.myHand.getDiamondsLength(),
                this.myHand.getClubsLength(),
                (double)(this.myHand.getMinimumHcp() + this.myHand.getMaximumHcp()) / 2,
                this.rho.getSpadesLength(),
                this.rho.getHeartsLength(),
                this.rho.getDiamondsLength(),
                this.rho.getClubsLength(),
                (double)(this.rho.getMinimumHcp() + this.rho.getMaximumHcp()) / 2,
                this.partner.getSpadesLength(),
                this.partner.getHeartsLength(),
                this.partner.getDiamondsLength(),
                this.partner.getClubsLength(),
                (double)(this.partner.getMinimumHcp() + this.partner.getMaximumHcp()) / 2,
                this.lho.getSpadesLength(),
                this.lho.getHeartsLength(),
                this.lho.getDiamondsLength(),
                this.lho.getClubsLength(),
                (double)(this.lho.getMinimumHcp() + this.lho.getMaximumHcp()) / 2,
                this.convertBidString(this.rhoBid),
                this.convertBidString(this.partnerBid),
                this.convertBidString(this.lhoBid),
                this.convertBidString(this.myLastBid),
        };
        
        return data;
    }
    
    public double[] getOutputs() {
        double[] outputs = {
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
                NeuralNetwork.LOW,
        };
        
        String bid = this.shouldBe;
        if (bid.equalsIgnoreCase("c")) {
            outputs[0] = NeuralNetwork.HIGH;
        } else if (bid.equalsIgnoreCase("d")) {
            outputs[1] = NeuralNetwork.HIGH;
        } else if (bid.equalsIgnoreCase("h")) {
            outputs[2] = NeuralNetwork.HIGH;
        } else if (bid.equalsIgnoreCase("s")){
            outputs[3] = NeuralNetwork.HIGH;
        } else if (bid.equalsIgnoreCase("n")){
            outputs[4] = NeuralNetwork.HIGH;
        } else if (bid.equalsIgnoreCase("p")) {
            outputs[5] = NeuralNetwork.HIGH;
        } else if (bid.equalsIgnoreCase("x")) {
            outputs[6] = NeuralNetwork.HIGH;
        } else if (bid.equalsIgnoreCase("r")) {
            outputs[7] = NeuralNetwork.HIGH;
        } else {
            outputs[8] = NeuralNetwork.HIGH;
        } 
        
        return outputs;
    }
    
    @Override
    public String toString() {
        String s = this.myHand + " " + this.rho + " " + this.rho.getMinimumHcp() + "-" + this.rho.getMaximumHcp();
        s += " " +  this.partner + " " + this.partner.getMinimumHcp() + "-" + this.partner.getMaximumHcp();
        s += " " + this.lho + " " + this.lho.getMinimumHcp() + "-" + this.lho.getMaximumHcp();
        s += " " + this.lhoBid + " " + this.partnerBid + " " + this.rhoBid + " " + this.myLastBid;
        s += " " + this.shouldBe;
        
        return s;
    }
}
