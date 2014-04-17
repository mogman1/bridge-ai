package com.shaunericcarlson.bridgeai.bidnetwork;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BidTrainingDataTest {
    
    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testString1() {
        String d1 = "QT3|A8752|AK2|53 ****|***|***|*** 0-27 ***|***|****|*** 0-27 ***|**|***|***** 0-27 - - - - H";
        BidTrainingData btd = new BidTrainingData(d1);
        double[] inputs = btd.getInputs();
        int p = 0;
        
        //test "my" hand
        p = 0*5;
        assertEquals(3, inputs[p+0], 0.0001); //spades length
        assertEquals(5, inputs[p+1], 0.0001); //hearts length
        assertEquals(3, inputs[p+2], 0.0001); //diamonds length
        assertEquals(2, inputs[p+3], 0.0001); //club length
        assertEquals(13, inputs[p+4], 0.0001); //HCP
        
        //test rho hand
        p = 1*5;
        assertEquals(4, inputs[p+0], 0.0001); //spades length
        assertEquals(3, inputs[p+1], 0.0001); //hearts length
        assertEquals(3, inputs[p+2], 0.0001); //diamonds length
        assertEquals(3, inputs[p+3], 0.0001); //club length
        assertEquals(13.5, inputs[p+4], 0.0001); //HCP
        
        //test partner hand
        p = 2*5;
        assertEquals(3, inputs[p+0], 0.0001); //spades length
        assertEquals(3, inputs[p+1], 0.0001); //hearts length
        assertEquals(4, inputs[p+2], 0.0001); //diamonds length
        assertEquals(3, inputs[p+3], 0.0001); //club length
        assertEquals(13.5, inputs[p+4], 0.0001); //HCP
        
        //test lho hand
        p = 3*5;
        assertEquals(3, inputs[p+0], 0.0001); //spades length
        assertEquals(2, inputs[p+1], 0.0001); //hearts length
        assertEquals(3, inputs[p+2], 0.0001); //diamonds length
        assertEquals(5, inputs[p+3], 0.0001); //club length
        assertEquals(13.5, inputs[p+4], 0.0001); //HCP
        
        //test bids
        assertEquals(0, inputs[20], 0.0001);
        assertEquals(0, inputs[21], 0.0001);
        assertEquals(0, inputs[22], 0.0001);
        assertEquals(0, inputs[23], 0.0001);
        
        //test outputs
        double[] outputs = btd.getOutputs();
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[0], 0.0001); //C
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[1], 0.0001); //D
        assertEquals(OneLevelNeuralNetwork.HIGH, outputs[2], 0.0001); //H
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[3], 0.0001); //S
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[4], 0.0001); //N
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[5], 0.0001); //D
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[6], 0.0001); //R
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[7], 0.0001); //P
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[8], 0.0001); //U
    }
    
    public void testString2() {
        String d1 = "Q52|KJ864|T62|A7 ****|***|***|*** 0-30 ***|***|****|*** 0-30 ***|**|***|***** 0-30 - - - - P";
        BidTrainingData btd = new BidTrainingData(d1);
        double[] inputs = btd.getInputs();
        int p = 0;
        
        //test "my" hand
        p = 0*5;
        assertEquals(3, inputs[p+0], 0.0001); //spades length
        assertEquals(5, inputs[p+1], 0.0001); //hearts length
        assertEquals(3, inputs[p+2], 0.0001); //diamonds length
        assertEquals(2, inputs[p+3], 0.0001); //club length
        assertEquals(13, inputs[p+4], 0.0001); //HCP
        
        //test rho hand
        p = 1*5;
        assertEquals(4, inputs[p+0], 0.0001); //spades length
        assertEquals(3, inputs[p+1], 0.0001); //hearts length
        assertEquals(3, inputs[p+2], 0.0001); //diamonds length
        assertEquals(3, inputs[p+3], 0.0001); //club length
        assertEquals(15, inputs[p+4], 0.0001); //HCP
        
        //test partner hand
        p = 2*5;
        assertEquals(3, inputs[p+0], 0.0001); //spades length
        assertEquals(3, inputs[p+1], 0.0001); //hearts length
        assertEquals(4, inputs[p+2], 0.0001); //diamonds length
        assertEquals(3, inputs[p+3], 0.0001); //club length
        assertEquals(15, inputs[p+4], 0.0001); //HCP
        
        //test lho hand
        p = 3*5;
        assertEquals(3, inputs[p+0], 0.0001); //spades length
        assertEquals(2, inputs[p+1], 0.0001); //hearts length
        assertEquals(3, inputs[p+2], 0.0001); //diamonds length
        assertEquals(5, inputs[p+3], 0.0001); //club length
        assertEquals(15, inputs[p+4], 0.0001); //HCP
        
        //test bids
        assertEquals(0, inputs[20], 0.0001);
        assertEquals(0, inputs[21], 0.0001);
        assertEquals(0, inputs[22], 0.0001);
        assertEquals(0, inputs[23], 0.0001);
        
        //test outputs
        double[] outputs = btd.getOutputs();
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[0], 0.0001); //1C
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[1], 0.0001); //1D
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[2], 0.0001); //1H
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[3], 0.0001); //1S
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[4], 0.0001); //1N
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[5], 0.0001); //D
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[6], 0.0001); //R
        assertEquals(OneLevelNeuralNetwork.HIGH, outputs[7], 0.0001); //P
        assertEquals(OneLevelNeuralNetwork.LOW, outputs[8], 0.0001); //U
    }
}
