package com.shaunericcarlson.bridgeai;

import java.util.HashSet;
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
        
        String[] test = "-".split("\\-");
        String[] shapes = {
                "***|*****|***|**",
        };
        Set<BidTrainingData> trainingData = new HashSet<BidTrainingData>();
        TrainingDataGenerator tdg;
        for (String shape : shapes) {
            tdg = new TrainingDataGenerator(new CrookedDealer(shape, 14, 12), "1H");
            trainingData.addAll(tdg.generate(1));
        }
        
        for (String shape : shapes) {
            tdg = new TrainingDataGenerator(new CrookedDealer(shape, 10), "P");
            trainingData.addAll(tdg.generate(1));
        }
        
    }
}
