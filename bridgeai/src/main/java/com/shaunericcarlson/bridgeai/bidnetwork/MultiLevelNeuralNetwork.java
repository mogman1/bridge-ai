package com.shaunericcarlson.bridgeai.bidnetwork;

import java.util.Random;

public class MultiLevelNeuralNetwork implements NeuralNetworkInterface {
	public static double ETA = 0.4;
	public static double MOMENTUM_RATE = 0.4;
	public static final double HIGH = 0.9;
	public static final double LOW = 0.1;
	
	private static final Random RAND = new Random();
	private static final double RANGE_MIN = -0.01;
	private static final double RANGE_MAX = 0.01;
	
	private double[][] nodes;
	private double[][][] weights;
	private double[][][] prevDeltaWeights;
	
	public MultiLevelNeuralNetwork(int numberOfInputs, int numberOfHiddens, int numberOfOutputs, int numberOfHiddenLayers) {
	    int levels = numberOfHiddenLayers + 2;
	    this.nodes = new double[levels][];
	    
	    this.nodes[0] = new double[numberOfInputs+1];
	    this.nodes[0][this.nodes[0].length-1] = 1;
	    
	    for (int i = 1; i < levels - 1; i++) { 
	        this.nodes[i] = new double[numberOfHiddens+1];
	        this.nodes[i][this.nodes[i].length-1] = 1;
	    }
	    
	    this.nodes[levels-1] = new double[numberOfOutputs];
	    
        this.weights = new double[this.nodes.length - 1][][];
        for (int i = 0; i < this.nodes.length - 1; i++) 
            this.weights[i] = this.initializeWeightMatrix(this.nodes[i].length, this.nodes[i+1].length, true);
        
        this.prevDeltaWeights = new double[this.nodes.length - 1][][];
        for (int i = 0; i < this.nodes.length - 1; i++) 
            this.prevDeltaWeights[i] = this.initializeWeightMatrix(this.nodes[i].length, this.nodes[i+1].length, false);
	}
	
    private void backpropagation(double[] targetOutput) {
        int outputLayer = this.nodes.length - 1;
        double[] deltaErrors = null;
        for (int layer = outputLayer; layer > 0; layer--) {
            double[] newDeltaErrors = new double[this.nodes[layer].length];
            for (int i = 0; i < this.nodes[layer].length; i++) {
                double errorTerm;
                if (layer == outputLayer) {
                    errorTerm = targetOutput[i] - this.nodes[layer][i];
                } else {
                    errorTerm = 0;
                    for (int j = 0; j < this.nodes[layer+1].length; j++) {
                        errorTerm += this.weights[layer][i][j] * deltaErrors[j];
                    }
                }
                
                newDeltaErrors[i] = this.nodes[layer][i] * (1 - this.nodes[layer][i]) * errorTerm;
                for (int j = 0; j < this.nodes[layer-1].length; j++) {
                    double deltaW = (MultiLevelNeuralNetwork.ETA * newDeltaErrors[i] * this.nodes[layer-1][j])
                            + (MultiLevelNeuralNetwork.MOMENTUM_RATE * this.prevDeltaWeights[layer-1][j][i]);
                    this.weights[layer-1][j][i] += deltaW;
                    this.prevDeltaWeights[layer-1][j][i] = deltaW;
                }
            }
            
            deltaErrors = newDeltaErrors;
        }
    }
    
	/**
	 * Creates a matrix of bias weights from one set of nodes to another.
	 * Either initializes them to random values or to zero.
	 * @param numOfLeftNodes
	 * @param numOfRightNodes
	 * @param random
	 * @return
	 */
	private double[][] initializeWeightMatrix(int numOfLeftNodes, int numOfRightNodes, boolean random) {
		double[][] matrix = new double[numOfLeftNodes][numOfRightNodes];
		for (int i = 0; i < numOfLeftNodes; i++) {
			for (int j = 0; j < numOfRightNodes; j++) {
				matrix[i][j] = (random) ? RANGE_MIN + (RANGE_MAX - RANGE_MIN) * RAND.nextDouble() : 0;
			}
		}
		
		return matrix;
	}
	
	/**
	 * Runs computation of output values from the values currently stored
	 * in inputs.
	 */
	private void compute(double[] inputs) {
        for (int i = 0; i < inputs.length; i++) this.nodes[0][i] = inputs[i];
	    for (int level = 1; level < this.nodes.length; level++) {
	        for (int i = 0; i < this.nodes[level].length; i++) {
                //don't change value of bias nodes, which are the last node on every layer except 
                //the last/output layer (which doesn't have a bias node)
                if ((i == this.nodes[level-1].length - 1) && (level < this.nodes.length - 1)) continue;
                
	            double total = 0;
	            for (int j = 0; j < this.nodes[level-1].length; j++) {
	                total += this.nodes[level-1][j] * this.weights[level-1][j][i];
	            }
	            
	            this.nodes[level][i] = this.sigmoid(total);
	        }
	    }
	}
	
	/**
	 * Computes the output values for a given set of inputs.
	 * @param inputs
	 * @return
	 */
	public double[] computeOutputs(double[] inputs) {
		if (inputs.length != this.nodes[0].length - 1) throw new RuntimeException("Invalid number of inputs");
		this.compute(inputs);
		
		return this.getOutputs();
	}
	
	public double getError(double[] targetOutputs) {
		if (targetOutputs.length != this.nodes[this.nodes.length-1].length) throw new RuntimeException("Invalid number of targetOutputs");
		double error = 0.0;
		for (int i = 0; i < targetOutputs.length; i++) {
			error += Math.pow((targetOutputs[i] - this.nodes[this.nodes.length-1][i]), 2);
		}
		
		return error;
	}
	
	/**
	 * Gets the outputs from the last calculation.  Does NOT recompute
	 * calculation of inputs, that is only done through computeOutputs.
	 * @return
	 */
	public double[] getOutputs() {
		double[] outputs = new double[this.nodes[this.nodes.length-1].length];
		for (int i = 0; i < outputs.length; i++) outputs[i] = this.nodes[this.nodes.length-1][i];
		
		return outputs;
	}

	/**
	 * Calculates 1 / (1 + e^(-val))
	 * @param val
	 * @return
	 */
	private double sigmoid(double val) {
		return (1 / (1 + Math.exp(-1 * val)));
	}
	
	public void train(double[] inputs, double[] targetOutput) {
		this.computeOutputs(inputs);
		this.backpropagation(targetOutput);
	}
}
