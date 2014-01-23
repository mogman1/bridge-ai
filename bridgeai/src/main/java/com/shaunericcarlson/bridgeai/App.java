package com.shaunericcarlson.bridgeai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.shaunericcarlson.bridgeai.bidnetwork.BidTrainingData;
import com.shaunericcarlson.bridgeai.bidnetwork.OneLevel;
import com.shaunericcarlson.bridgeai.bidnetwork.TrainingDataGenerator;
import com.shaunericcarlson.bridgeai.dealer.CrookedDealer;

public class App {
    public static void main( String[] args ) {
//        String[] shapes = {
//                "***|*****|***|**",
//                "***|*****|**|***",
//                "**|*****|***|***",
//                "***|*****|****|*",
//                "****|*****|***|*",
//                "*|*****|*****|**",
//                "**|******|***|**",
//                "**|******|**|***",
//        };
//        Set<BidTrainingData> trainingData = new HashSet<BidTrainingData>();
//        TrainingDataGenerator tdg;
//        for (String shape : shapes) {
//            tdg = new TrainingDataGenerator(new CrookedDealer(shape, 14, 12), "1H");
//            trainingData.addAll(tdg.generate(100));
//        }
//        
//        for (String shape : shapes) {
//            tdg = new TrainingDataGenerator(new CrookedDealer(shape, 10), "P");
//            trainingData.addAll(tdg.generate(100));
//        }
        
        Map<String, String> bids = new HashMap<String, String>();
        bids.put("***|*****|***|**", "H");
        bids.put("*****|***|***|**", "S");
        
        int TRAIN = 10;
        int TEST = 20;
        Set<BidTrainingData> trainingData = new HashSet<BidTrainingData>();
        Set<BidTrainingData> testHands = new HashSet<BidTrainingData>();
        TrainingDataGenerator tdg;
        for (Entry<String, String> bidShape : bids.entrySet()) {
            tdg = new TrainingDataGenerator(new CrookedDealer(bidShape.getKey(), 14, 12), bidShape.getValue());
            trainingData.addAll(tdg.generate(TRAIN));
        }
        
        for (Entry<String, String> bidShape : bids.entrySet()) {
            tdg = new TrainingDataGenerator(new CrookedDealer(bidShape.getKey(), 14, 12), bidShape.getValue());
            testHands.addAll(tdg.generate(TEST));
        }
        
        for (Entry<String, String> bidShape : bids.entrySet()) {
            tdg = new TrainingDataGenerator(new CrookedDealer(bidShape.getKey(), 10), "P");
            trainingData.addAll(tdg.generate(TRAIN));
        }
        
        for (Entry<String, String> bidShape : bids.entrySet()) {
            tdg = new TrainingDataGenerator(new CrookedDealer(bidShape.getKey(), 10), "P");
            testHands.addAll(tdg.generate(TEST));
        }
        
        OneLevel ol = new OneLevel(trainingData);
        ol.train();
        
        System.out.println("Training data results: ");
        for (BidTrainingData btd : trainingData) {
            System.out.println(btd + " - " + ol.getBid(btd));
        }
        
        System.out.println("\n\nNew (potentially) hand results: ");
        for (BidTrainingData btd : testHands) {
            System.out.println(btd + " - " + ol.getBid(btd));
        }
    }
}
