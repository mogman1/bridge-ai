package com.shaunericcarlson.bridgeai.bidnetwork.trainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.shaunericcarlson.bridgeai.bidnetwork.Bid;
import com.shaunericcarlson.bridgeai.bidnetwork.BidTrainingData;
import com.shaunericcarlson.bridgeai.bidnetwork.OneLevel;
import com.shaunericcarlson.bridgeai.bidnetwork.TrainingDataGenerator;

public class TrainOneLevelOpen {
    
    public static OneLevel train() {
        long start = System.currentTimeMillis();
        List<TrainingDataGenerator> bids = new ArrayList<TrainingDataGenerator>();
        bids.add(new TrainingDataGenerator("***|*****|***|** 12-14 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("***|*****|**|*** 12-14 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("***|*****|***|** 18-19 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("***|*****|**|*** 18-19 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("****|*****|**|** 12-19 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("****|*****|***|* 12-14 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("****|*****|***|* 15-17 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("****|*****|***|* 18-21 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("****|*****|*|*** 12-21 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("**|*****|****|** 12-19 - - - - - - H - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|***|** 12-14 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|***|** 18-19 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|**|*** 18-19 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|****|**|** 12-19 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|**|****|** 12-19 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|****|* 12-14 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|****|* 15-17 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|****|* 18-21 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|*|**** 12-21 - - - - - - S - - - -"));
        bids.add(new TrainingDataGenerator("****|****|***|** 12-14 - - - - - - D - - - -"));
        bids.add(new TrainingDataGenerator("****|***|****|** 12-14 - - - - - - D - - - -"));
        bids.add(new TrainingDataGenerator("***|***|****|*** 12-14 - - - - - - D - - - -"));
        bids.add(new TrainingDataGenerator("***|**|*****|*** 12-14 - - - - - - D - - - -"));
        bids.add(new TrainingDataGenerator("**|***|*****|*** 12-14 - - - - - - D - - - -"));
        bids.add(new TrainingDataGenerator("***|****|****|** 12-14 - - - - - - D - - - -"));
        bids.add(new TrainingDataGenerator("****|****|**|*** 12-14 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("****|***|***|*** 12-14 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|****|***|*** 12-14 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("****|***|**|**** 12-14 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|****|**|**** 12-14 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|***|**|***** 12-14 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|***|***|**** 12-14 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("****|****|**|*** 18-19 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("****|***|***|*** 18-19 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|****|***|*** 18-19 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("****|***|**|**** 18-19 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|****|**|**** 18-19 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|***|**|***** 18-19 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("***|***|***|**** 18-19 - - - - - - C - - - -"));
        bids.add(new TrainingDataGenerator("****|***|**|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("***|****|***|*** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("***|***|****|*** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("***|***|***|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("****|****|***|** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("****|***|****|** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("****|***|**|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("****|**|****|*** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("****|**|***|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("***|****|****|** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("***|****|**|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("***|**|****|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("**|****|****|*** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("**|****|***|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("**|***|****|**** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("**|*****|***|*** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("***|*****|***|** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("*****|**|***|*** 15-17 - - - - - - N - - - -"));
        bids.add(new TrainingDataGenerator("*****|***|***|** 15-17 - - - - - - N - - - -"));
        
        int TRAIN = 20;
        int TEST = 2000;
        Set<BidTrainingData> trainingData = new HashSet<BidTrainingData>();
        Set<BidTrainingData> testHands = new HashSet<BidTrainingData>();
        for (TrainingDataGenerator tdg : bids) {
            trainingData.addAll(tdg.generate(TRAIN));
            testHands.addAll(tdg.generate(TEST));
            
            int oldMin = tdg.minHcp;
            int oldMax = tdg.maxHcp;
            String oldBid = tdg.bidShouldBe;
            tdg.minHcp = 0;
            tdg.maxHcp = 10;
            tdg.bidShouldBe = "P";
            trainingData.addAll(tdg.generate(TRAIN));
            testHands.addAll(tdg.generate(TEST));
            tdg.bidShouldBe = oldBid;
            tdg.maxHcp = oldMax;
            tdg.minHcp = oldMin;
        }
        
        long genTime = System.currentTimeMillis();
        System.out.println("Time to generate test data: " + (float)(genTime - start) / 1000.0);
        OneLevel ol = new OneLevel(trainingData);
        ol.train();
        
        double trainingAccuracy = 0.0;
        double trainingCount = 0.0;
        System.out.println("Training set errors: ");
        for (BidTrainingData btd : trainingData) {
            trainingCount++;
            Bid computed = ol.getBid(btd);
            if (btd.shouldBe.equals(computed.toString())) {
                trainingAccuracy++;
            } else {
//                System.out.println(btd + " - " + ol.getBid(btd));
            }
        }
        
        double testAccuracy = 0.0;
        double testCount = 0.0;
        System.out.println("\n\nTest set errors: ");
        for (BidTrainingData btd : testHands) {
            testCount++;
            Bid computed = ol.getBid(btd);
            if (btd.shouldBe.equals(computed.toString())) {
                testAccuracy++;
            } else {
//                System.out.println(btd + " - " + ol.getBid(btd));
            }
        }
        
        System.out.println("Training accuracy: " + (trainingAccuracy / trainingCount));
        System.out.println("Test accuracy: " + (testAccuracy / testCount));
        System.out.println("Train time: " + (float)(System.currentTimeMillis() - genTime) / 1000.0);
        System.out.println("Total run time: " + (float)(System.currentTimeMillis() - start) / 1000.0);
        
        return ol;
    }
}
