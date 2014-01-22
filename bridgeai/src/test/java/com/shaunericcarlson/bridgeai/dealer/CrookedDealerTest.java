package com.shaunericcarlson.bridgeai.dealer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.shaunericcarlson.bridgeai.Hand;

public class CrookedDealerTest {
    
    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testFlatHand() {
        CrookedDealer cd = new CrookedDealer("****|***|***|***", 13, 13);
        Hand[] hands = cd.deal();
        assertEquals(13, hands[0].getMaximumHcp());
        assertEquals(13, hands[0].getMinimumHcp());
        assertEquals(4,  hands[0].getSpadesLength());
        assertEquals(3,  hands[0].getHeartsLength());
        assertEquals(3,  hands[0].getDiamondsLength());
        assertEquals(3,  hands[0].getClubsLength());
        
        assertEquals(27, hands[1].getMaximumHcp());
        assertEquals(0,  hands[1].getMinimumHcp());
        assertEquals(3,  hands[1].getSpadesLength());
        assertEquals(4,  hands[1].getHeartsLength());
        assertEquals(3,  hands[1].getDiamondsLength());
        assertEquals(3,  hands[1].getClubsLength());
        
        assertEquals(27, hands[2].getMaximumHcp());
        assertEquals(0,  hands[2].getMinimumHcp());
        assertEquals(3,  hands[2].getSpadesLength());
        assertEquals(3,  hands[2].getHeartsLength());
        assertEquals(4,  hands[2].getDiamondsLength());
        assertEquals(3,  hands[2].getClubsLength());
        
        assertEquals(27, hands[3].getMaximumHcp());
        assertEquals(0,  hands[3].getMinimumHcp());
        assertEquals(3,  hands[3].getSpadesLength());
        assertEquals(3,  hands[3].getHeartsLength());
        assertEquals(3,  hands[3].getDiamondsLength());
        assertEquals(4,  hands[3].getClubsLength());
    }
    
    @Test
    public void testSpecifyingRho() {
        CrookedDealer cd = new CrookedDealer("****|***|***|***", 13, 13);
        cd.addRho("*********|*|*|**", 10, 30);
        Hand[] hands = cd.deal();
        assertEquals(13, hands[0].getMaximumHcp());
        assertEquals(13, hands[0].getMinimumHcp());
        assertEquals(4,  hands[0].getSpadesLength());
        assertEquals(3,  hands[0].getHeartsLength());
        assertEquals(3,  hands[0].getDiamondsLength());
        assertEquals(3,  hands[0].getClubsLength());
        
        assertEquals(30, hands[1].getMaximumHcp());
        assertEquals(10, hands[1].getMinimumHcp());
        assertEquals(9,  hands[1].getSpadesLength());
        assertEquals(1,  hands[1].getHeartsLength());
        assertEquals(1,  hands[1].getDiamondsLength());
        assertEquals(2,  hands[1].getClubsLength());
        
        assertEquals(27, hands[2].getMaximumHcp());
        assertEquals(0,  hands[2].getMinimumHcp());
        assertEquals(0,  hands[2].getSpadesLength());
        assertEquals(5,  hands[2].getHeartsLength());
        assertEquals(4,  hands[2].getDiamondsLength());
        assertEquals(4,  hands[2].getClubsLength());
        
        assertEquals(27, hands[3].getMaximumHcp());
        assertEquals(0,  hands[3].getMinimumHcp());
        assertEquals(0,  hands[3].getSpadesLength());
        assertEquals(4,  hands[3].getHeartsLength());
        assertEquals(5,  hands[3].getDiamondsLength());
        assertEquals(4,  hands[3].getClubsLength());
    }
}
