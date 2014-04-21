package com.shaunericcarlson.bridgeai.doubledummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shaunericcarlson.bridgeai.Dealer;
import com.shaunericcarlson.bridgeai.Direction;
import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.dealer.FairDealer;

public class ThreadedDoubleDummy extends Thread {
    private DoubleDummyPool pool;
    private DoubleDummy dd;
    private Dealer dealer;
    public boolean running = true;
    
    private static Logger log = LoggerFactory.getLogger(ThreadedDoubleDummy.class);
    
    public ThreadedDoubleDummy(DoubleDummy dd, DoubleDummyPool pool) {
        this.dd = dd;
        this.dealer = new FairDealer();
        this.pool = pool;
    }
    
    @Override
    public void run() {
        while (this.running) { 
            Hand[] hands = this.dealer.deal();
            try {
                this.pool.addResult(this.dd.evaluate(hands, Direction.NORTH));
//                Thread.sleep(100);
            } catch (Exception e) {
                log.error("Error performing evaluation, skipping", e);
            }
        }
    }
    
}
