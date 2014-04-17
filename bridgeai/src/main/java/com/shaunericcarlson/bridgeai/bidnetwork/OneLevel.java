package com.shaunericcarlson.bridgeai.bidnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

public class OneLevel {
    private final static double ERROR_TOLERANCE = 0.05;
    private final static int NUMBER_OF_INPUTS = 9;
    private final static int NUMBER_OF_OUTPUTS = 9;
    private OneLevelNeuralNetwork nn;
    private Collection<BidTrainingData> bids;
    
    public OneLevel(Collection<BidTrainingData> bids) {
        int hiddens = (NUMBER_OF_INPUTS + NUMBER_OF_OUTPUTS) / 2 + 2;
//        int hiddens = 7;
        this.nn = new OneLevelNeuralNetwork(NUMBER_OF_INPUTS, hiddens, NUMBER_OF_OUTPUTS);
        this.bids = bids;
    }
    
    public OneLevel(BufferedReader r) throws IOException {
        this.nn = new OneLevelNeuralNetwork(r);
    }
    
    public Bid getBid(BidTrainingData btd) {
        double[] outputs = this.nn.computeOutputs(btd.getInputs());
        double maxBid = -1;
        Bid bid = Bid.UNKNOWN;
        if (outputs[0] > maxBid) {
            bid = Bid.CLUB;
            maxBid = outputs[0];
        }
        
        if (outputs[1] > maxBid) {
            bid = Bid.DIAMOND;
            maxBid = outputs[1];
        }
        
        if (outputs[2] > maxBid) {
            bid = Bid.HEART;
            maxBid = outputs[2];
        }
        
        if (outputs[3] > maxBid) {
            bid = Bid.SPADE;
            maxBid = outputs[3];
        }
        
        if (outputs[4] > maxBid) {
            bid = Bid.NOTRUMP;
            maxBid = outputs[4];
        }
        
        if (outputs[5] > maxBid) {
            bid = Bid.DOUBLE;
            maxBid = outputs[5];
        }
        
        if (outputs[6] > maxBid) {
            bid = Bid.REDOUBLE;
            maxBid = outputs[6];
        }
        
        if (outputs[7] > maxBid) {
            bid = Bid.PASS;
            maxBid = outputs[7];
        }
        
        if (outputs[8] > maxBid) {
            bid = Bid.UNKNOWN;
            maxBid = outputs[8];
        }
        
        return bid;
    }
    
    private double getError(boolean print) {
        double error = 0.0;
        for (BidTrainingData btd : this.bids) {
            double[] output = this.nn.computeOutputs(btd.getInputs());
            double[] target = btd.getOutputs();
            
            for (int i = 0; i < output.length; i++) {
                error += Math.pow(target[i] - output[i], 2);
                if (print) {
                    System.out.println(target[i] + "\t" + output[i] + "\t" + (target[i] - output[i]));
                }
            }
            
            if (print) System.out.println("");
        }
        
        return error / this.bids.size();
    }
    
    public void train() {
        int count = 0;
        while (this.getError(false) > OneLevel.ERROR_TOLERANCE) {
            count++;
            for (BidTrainingData btd : this.bids) {
                this.nn.train(btd.getInputs(), btd.getOutputs());
            }
            
            if (count % 10000 == 0) {
                System.out.println(count + ":\t" + this.getError(false));
            }
        }
        System.out.println(count);
    }
    
    public String toString() {
        return this.nn.toString();
    }
}
