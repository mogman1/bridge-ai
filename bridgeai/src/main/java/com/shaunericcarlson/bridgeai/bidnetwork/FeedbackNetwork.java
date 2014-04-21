package com.shaunericcarlson.bridgeai.bidnetwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shaunericcarlson.bridgeai.doubledummy.BcalcDoubleDummy;
import com.shaunericcarlson.bridgeai.doubledummy.DoubleDummyPool;
import com.shaunericcarlson.bridgeai.doubledummy.DoubleDummyResult;

public class FeedbackNetwork {
    private final static int NUMBER_OF_INPUTS = 13;
    private final static int NUMBER_OF_OUTPUTS = 13;
    private NeuralNetworkInterface nn;
    
    private static Logger log = LoggerFactory.getLogger(FeedbackNetwork.class);
    
    public FeedbackNetwork(int hiddenNodesPerLayer, int hiddenLayers) {
        this.nn = new MultiLevelNeuralNetwork(NUMBER_OF_INPUTS, hiddenNodesPerLayer, NUMBER_OF_OUTPUTS, hiddenLayers);
    }
    
    public Bid getBid(FeedbackBidTrainingData bidder) {
        double[] outputs = this.nn.computeOutputs(bidder.getInputs());
        
        //get strain
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
            bid = Bid.PASS;
            maxBid = outputs[5];
        }
        
        //determine level
        maxBid = -1;
        if (outputs[6] > maxBid) {
            bid.level = 1;
            maxBid = outputs[6];
        }
        
        if (outputs[7] > maxBid) {
            bid.level = 2;
            maxBid = outputs[7];
        }
        
        if (outputs[8] > maxBid) {
            bid.level = 3;
            maxBid = outputs[8];
        }
        
        if (outputs[9] > maxBid) {
            bid.level = 4;
            maxBid = outputs[9];
        }
        
        if (outputs[10] > maxBid) {
            bid.level = 5;
            maxBid = outputs[10];
        }
        
        if (outputs[11] > maxBid) {
            bid.level = 6;
            maxBid = outputs[11];
        }
        
        if (outputs[12] > maxBid) {
            bid.level = 7;
            maxBid = outputs[12];
        }
        
        if (bid == Bid.PASS) bid.level = 0;
        
        return bid;
    }
    
    private void invalidBid() {
        final int SUIT_START = 0;
        final int SUIT_LIMIT = 6;
        final int LEVEL_START = 6;
        final int LEVEL_LIMIT = 13;
        
        double[] outputs = this.nn.getOutputs();
        double[] shouldBe = new double[this.nn.getOutputs().length];
        System.arraycopy(outputs, 0, shouldBe, 0, outputs.length);
        
        int idxToZero = SUIT_START;
        double t = -1;
        for (int i = SUIT_START; i < SUIT_LIMIT; i++) {
            if (outputs[i] > t) {
                t = outputs[i];
                idxToZero = i;
            }
        }
        
        shouldBe[idxToZero] = 0;
        idxToZero = LEVEL_START;
        t = -1;
        for (int i = LEVEL_START; i < LEVEL_LIMIT; i++) {
            if (outputs[i] > t) {
                t = outputs[i];
                idxToZero = i;
            }
        }
        
        shouldBe[idxToZero] = 0;
        this.nn.backpropagation(shouldBe);
    }
    
    private void setOptimalBid(Bid optimal) {
        double[] shouldBe = new double[this.nn.getOutputs().length];
        for (int i = 0; i < shouldBe.length; i++) shouldBe[i] = 0.0;
        
        switch (optimal) {
            case CLUB:      shouldBe[0] = 1.0; break;
            case DIAMOND:   shouldBe[1] = 1.0; break;
            case HEART:     shouldBe[2] = 1.0; break;
            case SPADE:     shouldBe[3] = 1.0; break;
            case NOTRUMP:   shouldBe[4] = 1.0; break;
            default:
            case PASS:      shouldBe[5] = 1.0; break;
        }
        
        if (optimal.level > 0) {
            int bidLevelStart = 5;
            shouldBe[optimal.level + bidLevelStart] = 1.0;
        }
        
        this.nn.backpropagation(shouldBe);
    }
    
    public void train() {
        int count = 1;
        int validBids = 0;
        int successes = 0;
        DoubleDummyPool pool = new DoubleDummyPool();
        pool.addDoubleDummy(3);
        pool.start();
        try {
            log.debug("Sleeping to build up pool...");
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            log.debug("error sleeping", e);
        }
        
        while (true) {
            DoubleDummyResult ddr = pool.getResult();
            FeedbackBidTrainingData opener = new FeedbackBidTrainingData(ddr.getOpener());
            FeedbackBidTrainingData respdr = new FeedbackBidTrainingData(ddr.getResponder());
            FeedbackBidTrainingData bidder = opener;
            boolean isOpener = true;
            while (true) {
                Bid b = this.getBid(bidder);
                if (!bidder.isOutputValid(b)) {
                    this.invalidBid();
                    break;
                }
                
                opener.addBid(b);
                respdr.addBid(b);
                if (bidder.isFinished()) {
                    Bid contract = bidder.getContract();
                    Bid optimal = ddr.getOptimalContract(contract);
                    if (contract != Bid.PASS) {
                        log.debug("Arrived contract: " + contract.altToString());
                        log.debug("Optimal contract: " + optimal.altToString());
                    }
                    
                    this.setOptimalBid(optimal);
                    if (contract == optimal && contract.level == optimal.level) successes++;
                    validBids++;
                    break;
                }
                
                bidder = (isOpener) ? respdr : opener;
                isOpener = !isOpener;
            }
            
            if (count % 100 == 0) {
                log.debug("Pool size: " + pool.getSize());
                log.debug(count + "\t" + validBids + "\t" + successes + "\t" + ((double)validBids/count) + "\t" + ((double)successes/count));
            }
            count++;
        }
    }
    
    public String toString() {
        return this.nn.toString();
    }
}
