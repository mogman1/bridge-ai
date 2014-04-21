package com.shaunericcarlson.bridgeai.doubledummy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleDummyPool {
    private List<ThreadedDoubleDummy> threads;
    private Queue<DoubleDummyResult> results;
    
    private static Logger log = LoggerFactory.getLogger(DoubleDummyPool.class);
    
    public DoubleDummyPool() {
        this.threads = new ArrayList<ThreadedDoubleDummy>();
        this.results = new LinkedList<DoubleDummyResult>();
    }
    
    public void addDoubleDummy(DoubleDummy dd) {
        this.threads.add(new ThreadedDoubleDummy(dd, this));
    }
    
    public void addDoubleDummy(int number) {
        for (int i = 0; i < number; i++) this.addDoubleDummy(new BcalcDoubleDummy());
    }
    
    public void addResult(DoubleDummyResult ddr) {
        synchronized(this) {
            this.results.add(ddr);
        }
    }
    
    public DoubleDummyResult getResult() {
//        if (this.results.size() == 0) log.debug("Waiting for double dummy analysis...");
        while (this.results.size() == 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.debug("error sleeping", e);
            }
        }
        
        DoubleDummyResult ddr;
        synchronized(this) {
            ddr = this.results.remove();
        }
        
        return ddr;
    }
    
    public int getSize() {
        return this.results.size();
    }
    
    public void start() {
        for (ThreadedDoubleDummy t : this.threads) t.start();
    }
    
    public void stop() {
        for (ThreadedDoubleDummy t : this.threads) t.running = false;
    }
}
