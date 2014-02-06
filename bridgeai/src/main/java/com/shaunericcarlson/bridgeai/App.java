package com.shaunericcarlson.bridgeai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.shaunericcarlson.bridgeai.bidnetwork.Bid;
import com.shaunericcarlson.bridgeai.bidnetwork.BidTrainingData;
import com.shaunericcarlson.bridgeai.bidnetwork.OneLevel;
import com.shaunericcarlson.bridgeai.bidnetwork.trainer.TrainOneLevelOpen;
import com.shaunericcarlson.bridgeai.bidnetwork.trainer.TrainOneLevelResponse;
import com.shaunericcarlson.bridgeai.dealer.FairDealer;

public class App {
    public static void main( String[] args ) throws IOException {
        OneLevel open;
        OneLevel response;
//        open = TrainOneLevelOpen.train();
//        System.out.println(open);System.exit(0);
//        response = TrainOneLevelResponse.train();
//        System.out.println(response);System.exit(0);
        open = new OneLevel(new BufferedReader(new FileReader(new File("src/main/resources/openNetwork.txt"))));
        response = new OneLevel(new BufferedReader(new FileReader(new File("src/main/resources/responseNetwork.txt"))));
        
        Dealer d = new FairDealer();
        for (int i = 0; i < 40; i++) {
            Hand[] hands = d.deal();
            String mHand = hands[0].toString() + " " + " - 0-0 - 0-0 - 0-0 " + "- - - - U";
            String pHand = hands[2].toString() + " " + " - 0-0 - 0-0 - 0-0 ";
            Bid oBid = open.getBid(new BidTrainingData(mHand));
            Bid rBid;
            switch(oBid) {
                case CLUB:
                    pHand += "P 1C - - U";
                    rBid = response.getBid(new BidTrainingData(pHand));
                    break;
                case DIAMOND:
                    pHand += "P 1D - - U";
                    rBid = response.getBid(new BidTrainingData(pHand));
                    break;
                case HEART:
                    pHand += "P 1H - - U";
                    rBid = response.getBid(new BidTrainingData(pHand));
                    break;
                case SPADE:
                    pHand += "P 1S - - U";
                    rBid = response.getBid(new BidTrainingData(pHand));
                    break;
                case NOTRUMP:
                    pHand += "P 1N - - U";
                    rBid = response.getBid(new BidTrainingData(pHand));
                    break;
                case PASS:
                    pHand += "P P - - U";
                    rBid = open.getBid(new BidTrainingData(pHand));
                    break;
                default:
                    rBid = null;
                    break;
            }
            
            System.out.println(hands[0] + " " + hands[0].getMaximumHcp() + " " + oBid);
            System.out.println(hands[2] + " " + hands[2].getMaximumHcp() + " " + rBid);
            System.out.println();
        }
    }
}
