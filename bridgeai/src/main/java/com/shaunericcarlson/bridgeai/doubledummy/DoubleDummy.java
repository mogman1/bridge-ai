package com.shaunericcarlson.bridgeai.doubledummy;

import com.shaunericcarlson.bridgeai.Direction;
import com.shaunericcarlson.bridgeai.Hand;

abstract public class DoubleDummy {
    private boolean isValid;
    
    public DoubleDummy() {
        this.isValid = false;
    }
    
    /**
     * Does a double dummy analysis on a set of hands, storing the result in data.  If there was an error
     * during the analysis for any reason, a DoubleDummyException will be thrown.
     * @param hands
     * @param dealer
     * @param data
     * @return
     * @throws DoubleDummyException 
     */
    abstract public DoubleDummyResult evaluate(Hand[] hands, Direction dealer) throws DoubleDummyException;
    
    public boolean isValid() {
        return this.isValid;
    }
}
