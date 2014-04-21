package com.shaunericcarlson.bridgeai.bidnetwork;

public interface NeuralNetworkInterface {
    
    public void backpropagation(double[] targetOutput);
    
    /**
     * Computes the output values for a given set of inputs.
     * @param inputs
     * @return
     */
    public double[] computeOutputs(double[] inputs);
    
    public double getError(double[] targetOutputs);
    
    /**
     * Gets the outputs from the last calculation.  Does NOT recompute
     * calculation of inputs, that is only done through computeOutputs.
     * @return
     */
    public double[] getOutputs();
    
    public void train(double[] inputs, double[] targetOutput);
}
