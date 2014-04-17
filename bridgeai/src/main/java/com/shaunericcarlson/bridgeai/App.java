package com.shaunericcarlson.bridgeai;

import com.shaunericcarlson.bridgeai.dealer.FairDealer;
import com.shaunericcarlson.bridgeai.doubledummy.BcalcDoubleDummy;
import com.shaunericcarlson.bridgeai.doubledummy.DoubleDummy;

public class App {
    public static void main(String[] args) {
        Dealer d = new FairDealer();
        DoubleDummy dd = new BcalcDoubleDummy();
        
        long start = System.currentTimeMillis();
        final int ITERS = 1000;
        for (int i = 0; i < ITERS; i++)
            dd.evaluateDeal(d.deal(), d.getDealer());
        long end = System.currentTimeMillis();
        
        System.out.println(end - start);
        System.out.println(((end - start) / 1000.0) / ITERS);
    }
    
//    public static void meh(String[] args) throws IOException {
//            Dealer d = new FairDealer();
//            DoubleDummy dd = new DoubleDummy();
//            File deals = new File("deals.txt");
//            Charset charset = Charset.forName("US-ASCII");
//            BufferedWriter writer = Files.newBufferedWriter(deals.toPath(), charset);
//            Direction dlr = Direction.NORTH;
//            for (int i = 0; i < 1000000; i++) {
//                String s = dd.getPbnRepresentation(d.deal(), dlr) + "\n";
//                writer.write(s, 0, s.length());
//                dlr = dlr.getNext();
//                if (i % 10000 == 0) System.out.println(i);
//            }
//    }
    
//    public static void main(String[] args) throws IOException {
//        OneLevel open;
//        OneLevel response;
////        open = TrainOneLevelOpen.train();
////        System.out.println(open);System.exit(0);
////        response = TrainOneLevelResponse.train();
////        System.out.println(response);System.exit(0);
//        open = new OneLevel(new BufferedReader(new FileReader(new File("src/main/resources/openNetwork.txt"))));
//        response = new OneLevel(new BufferedReader(new FileReader(new File("src/main/resources/responseNetwork.txt"))));
//        
//        Dealer d = new FairDealer();
//        for (Hand h : d.deal()) {
//            System.out.println(h);
//        }
//        System.exit(0);
//        for (int i = 0; i < 40; i++) {
//            Hand[] hands = d.deal();
//            String mHand = hands[0].toString() + " " + " - 0-0 - 0-0 - 0-0 " + "- - - - U";
//            String pHand = hands[2].toString() + " " + " - 0-0 - 0-0 - 0-0 ";
//            Bid oBid = open.getBid(new BidTrainingData(mHand));
//            Bid rBid;
//            switch(oBid) {
//                case CLUB:
//                    pHand += "P 1C - - U";
//                    rBid = response.getBid(new BidTrainingData(pHand));
//                    break;
//                case DIAMOND:
//                    pHand += "P 1D - - U";
//                    rBid = response.getBid(new BidTrainingData(pHand));
//                    break;
//                case HEART:
//                    pHand += "P 1H - - U";
//                    rBid = response.getBid(new BidTrainingData(pHand));
//                    break;
//                case SPADE:
//                    pHand += "P 1S - - U";
//                    rBid = response.getBid(new BidTrainingData(pHand));
//                    break;
//                case NOTRUMP:
//                    pHand += "P 1N - - U";
//                    rBid = response.getBid(new BidTrainingData(pHand));
//                    break;
//                case PASS:
//                    pHand += "P P - - U";
//                    rBid = open.getBid(new BidTrainingData(pHand));
//                    break;
//                default:
//                    rBid = null;
//                    break;
//            }
//            
//            System.out.println(hands[0] + " " + hands[0].getMaximumHcp() + " " + oBid);
//            System.out.println(hands[2] + " " + hands[2].getMaximumHcp() + " " + rBid);
//            System.out.println();
//        }
//    }
}
