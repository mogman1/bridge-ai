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
    public Bid shouldBe;
    
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
        this.shouldBe = Bid.getFromChar(components[11].charAt(0));
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
                
//                this.rho.getSpadesLength(),
//                this.rho.getHeartsLength(),
//                this.rho.getDiamondsLength(),
//                this.rho.getClubsLength(),
//                (double)(this.rho.getMinimumHcp() + this.rho.getMaximumHcp()) / 2,
                
//                this.partner.getSpadesLength(),
//                this.partner.getHeartsLength(),
//                this.partner.getDiamondsLength(),
//                this.partner.getClubsLength(),
//                (double)(this.partner.getMinimumHcp() + this.partner.getMaximumHcp()) / 2,
                
//                this.lho.getSpadesLength(),
//                this.lho.getHeartsLength(),
//                this.lho.getDiamondsLength(),
//                this.lho.getClubsLength(),
//                (double)(this.lho.getMinimumHcp() + this.lho.getMaximumHcp()) / 2,
                this.convertBidString(this.rhoBid),
                this.convertBidString(this.partnerBid),
                this.convertBidString(this.lhoBid),
                this.convertBidString(this.myLastBid),
        };
        
        return data;
    }
    
    public double[] getOutputs() {
        double[] outputs = {
                NeuralNetwork.LOW, //club
                NeuralNetwork.LOW, //diamond
                NeuralNetwork.LOW, //heart
                NeuralNetwork.LOW, //spade
                NeuralNetwork.LOW, //notrump
                NeuralNetwork.LOW, //double
                NeuralNetwork.LOW, //redouble
                NeuralNetwork.LOW, //pass
                NeuralNetwork.LOW, //unknown
        };
        
        switch (this.shouldBe) {
            case CLUB:
                outputs[0] = NeuralNetwork.HIGH; break;
            case DIAMOND:
                outputs[1] = NeuralNetwork.HIGH; break;
            case HEART:
                outputs[2] = NeuralNetwork.HIGH; break;
            case SPADE:
                outputs[3] = NeuralNetwork.HIGH; break;
            case NOTRUMP:
                outputs[4] = NeuralNetwork.HIGH; break;
            case DOUBLE:
                outputs[5] = NeuralNetwork.HIGH; break;
            case REDOUBLE:
                outputs[6] = NeuralNetwork.HIGH; break;
            case PASS:
                outputs[7] = NeuralNetwork.HIGH; break;
            default:
                outputs[8] = NeuralNetwork.HIGH; break;
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
